package com.example.miniprojectsawit.utils;

import com.example.miniprojectsawit.enums.ErrorsEnum;
import com.example.miniprojectsawit.exception.BusinessException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;

@Component
public class JwtUtils {
    @Value("${jwt.expiration}")
    private long expiration;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.audience}")
    private String audience;

    @Value("${keypair.privateKey}")
    private String privatekey;

    @Value("${keypair.publicKey}")
    private String publicKey;

    public String generateToken(Object data) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expiration);
        return Jwts.builder()
                .setSubject(data.toString())
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .setIssuer(issuer)
                .setAudience(audience)
                .signWith(generatePrivateKey(privatekey))
                .compact();
    }

    public void parseSubject(String token) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(generatePublicKeyFromString(publicKey))
                .build()
                .parseClaimsJws(token)
                .getBody();

        Long now = new Date().getTime();

        if(now > claims.getExpiration().getTime()) {
            throw new BusinessException(ErrorsEnum.TOKEN_EXPIRED);
        }
    }

    private PrivateKey generatePrivateKey(String privateKey) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        StringBuilder pkcs8Lines = new StringBuilder();
        BufferedReader rdr = new BufferedReader(new StringReader(privateKey));
        String line;
        while ((line = rdr.readLine()) != null) {
            pkcs8Lines.append(line);
        }

        // Remove the "BEGIN" and "END" lines, as well as any whitespace

        String pkcs8Pem = pkcs8Lines.toString();
        pkcs8Pem = pkcs8Pem.replace("-----BEGIN PRIVATE KEY-----", "");
        pkcs8Pem = pkcs8Pem.replace("-----END PRIVATE KEY-----", "");
        pkcs8Pem = pkcs8Pem.replaceAll("\\s+","");

        // Base64 decode the result
        byte [] pkcs8EncodedBytes = Base64.decodeBase64(pkcs8Pem);

        // extract the private key

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pkcs8EncodedBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey pk = kf.generatePrivate(keySpec);
        return pk;
    }

    private PublicKey generatePublicKeyFromString(String publicKeyString) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] publicKeyBytes = Base64.decodeBase64(publicKeyString);

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        return keyFactory.generatePublic(keySpec);
    }
}
