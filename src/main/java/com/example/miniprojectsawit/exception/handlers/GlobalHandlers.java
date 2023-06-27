package com.example.miniprojectsawit.exception.handlers;

import com.example.miniprojectsawit.exception.BusinessException;
import com.example.miniprojectsawit.response.BaseResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalHandlers {

    @ExceptionHandler
    public ResponseEntity<BaseResponse> handleBusinessException(BusinessException businessException) {
        BaseResponse baseResponse = new BaseResponse(businessException.getErrors().getCode(),
                businessException.getErrors().getMessage(),
                null);
        return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
    }

}
