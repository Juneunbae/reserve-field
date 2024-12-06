package com.example.reservefield.exception;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<Map<String, String>> ExceptionHandler(CustomException e) {
        HttpHeaders headers = new HttpHeaders();

        Map<String, String> map = new HashMap<>();
        map.put("error code", Integer.toString(e.getHttpStatus().value()));
        map.put("error message", e.getMessage());

        log.error(e.getMessage());
        return new ResponseEntity<>(map, headers, e.getHttpStatus());
    }
}