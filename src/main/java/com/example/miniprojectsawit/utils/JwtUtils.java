package com.example.miniprojectsawit.utils;

import com.example.miniprojectsawit.enums.ErrorsEnum;
import com.example.miniprojectsawit.exception.BusinessException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.security.PublicKey;
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
    private PrivateKey privatekey;

    @Value("${keypair.publicKey}")
    private PublicKey publicKey;

    public String generateToken(Object data) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setSubject(data.toString())
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .setIssuer(issuer)
                .setAudience(audience)
                .signWith(privatekey)
                .compact();
    }

    public void parseSubject(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Long now = new Date().getTime();

        if(now > claims.getExpiration().getTime()) {
            throw new BusinessException(ErrorsEnum.TOKEN_EXPIRED);
        }
    }
}
