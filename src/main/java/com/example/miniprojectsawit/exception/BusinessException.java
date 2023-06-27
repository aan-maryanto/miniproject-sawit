package com.example.miniprojectsawit.exception;

import com.example.miniprojectsawit.enums.ErrorsEnum;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusinessException extends RuntimeException {

    private ErrorsEnum errors;

}
