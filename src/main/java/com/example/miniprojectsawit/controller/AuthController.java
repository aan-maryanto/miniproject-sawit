package com.example.miniprojectsawit.controller;

import com.example.miniprojectsawit.dto.LoginReqDTO;
import com.example.miniprojectsawit.dto.RegisterReqDTO;
import com.example.miniprojectsawit.response.BaseResponse;
import com.example.miniprojectsawit.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@RequestBody RegisterReqDTO request) {
        authService.saveUser(request);
        return ResponseEntity.ok("success register");
    }

    @PostMapping(value = "/login")
    public ResponseEntity<BaseResponse> login(@RequestBody LoginReqDTO request) {
        return authService.login(request);
    }

    @PostMapping(value = "/update-name")
    public ResponseEntity<BaseResponse> updateName(@RequestHeader(value = "Authorization") String token, @RequestBody RegisterReqDTO request) {
        return authService.updateName(token, request);
    }

}
