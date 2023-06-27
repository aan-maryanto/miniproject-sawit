package com.example.miniprojectsawit.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse {
    private String code;
    private String message;
    private Object data;
}
