package com.example.miniprojectsawit.dto;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class RegisterReqDTO {

    @NotBlank
    @Max(value = 60)
    private String name;

    @NotBlank
    @Size(min = 6, max = 16)
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\\\d).+$")
    private String password;

    @NotBlank
    @Size(min = 10, max = 13)
    @Pattern(regexp = "^08\\\\d*$")
    private String phone;

}
