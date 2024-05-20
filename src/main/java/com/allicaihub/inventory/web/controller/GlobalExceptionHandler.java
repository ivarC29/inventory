package com.allicaihub.inventory.web.controller;

import com.allicaihub.inventory.web.dto.ErrorDto;
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

}
