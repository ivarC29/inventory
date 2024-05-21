package com.allicaihub.inventory.web.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
public class BusinessException extends RuntimeException {
    private String code;
    private HttpStatus httpStatus;

    public BusinessException(String code, HttpStatus status, String message) {
        super(message);
        this.code = code;
        this.httpStatus = status;
    }
}
