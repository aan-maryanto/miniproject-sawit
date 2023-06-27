package com.example.miniprojectsawit.dto;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class LoginReqDTO {

    @NotBlank
    @Size(min = 10, max = 13)
    @Pattern(regexp = "^08\\\\d*$")
    private String phone;

    private String password;

}
