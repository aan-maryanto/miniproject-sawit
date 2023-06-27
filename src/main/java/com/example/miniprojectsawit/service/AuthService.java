package com.example.miniprojectsawit.service;

import com.example.miniprojectsawit.dto.LoginReqDTO;
import com.example.miniprojectsawit.dto.RegisterReqDTO;
import com.example.miniprojectsawit.entity.User;
import com.example.miniprojectsawit.enums.ErrorsEnum;
import com.example.miniprojectsawit.exception.BusinessException;
import com.example.miniprojectsawit.repository.UserRepository;
import com.example.miniprojectsawit.response.BaseResponse;
import com.example.miniprojectsawit.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public User saveUser(RegisterReqDTO request) {
        User user = User.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        return userRepository.save(user);
    }

    public ResponseEntity<BaseResponse> login(LoginReqDTO request) {
        String hashPassword = passwordEncoder.encode(request.getPassword());
        Optional<User> user = userRepository.findFirstByPhoneAndPassword(request.getPhone(), hashPassword);
        if (user.isEmpty()) {
            throw new BusinessException(ErrorsEnum.USER_NOT_FOUND);
        }
        var data = jwtUtils.generateToken(user.get());
        BaseResponse response = new BaseResponse("200", "success login", data);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<BaseResponse> updateName(String token, RegisterReqDTO request) {
        jwtUtils.parseSubject(token);
        String hashPassword = passwordEncoder.encode(request.getPassword());
        Optional<User> users = userRepository.findFirstByPhoneAndPassword(request.getPhone(), hashPassword);
        if(users.isEmpty()) {
            throw new BusinessException(ErrorsEnum.USER_NOT_FOUND);
        }
        var user = users.get();
        userRepository.updateByName(request.getName(), user.getPhone());
        BaseResponse res = new BaseResponse("200", "success", new Object());
        return ResponseEntity.ok(res);
    }
}
