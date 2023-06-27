package com.example.miniprojectsawit.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorsEnum {

    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "User Not Found", "user_not_found"),
    TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, "Token expired", "token_expired");


    private final HttpStatus status;
    private final String message;
    private final String code;

}
