package com.allicaihub.inventory.web.controller;

import com.allicaihub.inventory.web.dto.ErrorDto;
import com.allicaihub.inventory.web.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public Mono<ResponseEntity<ErrorDto>> handleRuntimeException(RuntimeException ex) {
        ErrorDto error = ErrorDto.builder().code("ERR-INVENTORY-500:").message(ex.getMessage()).build();
        return Mono.just( ResponseEntity.internalServerError().body(error));
    }

    @ExceptionHandler(BusinessException.class)
    public Mono<ResponseEntity<ErrorDto>> handleBusinessException(BusinessException ex) {
        ErrorDto error = ErrorDto.builder().code("ERR-INVENTORY-900:").message(ex.getMessage()).build();
        return Mono.just( new ResponseEntity<>(error , ex.getHttpStatus() ) );
    }

}
