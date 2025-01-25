package com.example.reservefield.exception;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Map<String, String>> AllExceptionHandler(CustomException e) {
        HttpHeaders headers = new HttpHeaders();

        Map<String, String> map = new HashMap<>();
        map.put("error code", Integer.toString(e.getHttpStatus().value()));
        map.put("error message", e.getMessage());

        log.error("에러 : {}", e.getMessage());
        return new ResponseEntity<>(map, headers, e.getHttpStatus());
    }

    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<Map<String, String>> ExceptionHandler(CustomException e) {
        HttpHeaders headers = new HttpHeaders();

        Map<String, String> map = new HashMap<>();
        map.put("error code", Integer.toString(e.getHttpStatus().value()));
        map.put("error message", e.getMessage());

        log.error(e.getMessage());
        return new ResponseEntity<>(map, headers, e.getHttpStatus());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("Invalid json", e);
        HttpHeaders headers = new HttpHeaders();

        Map<String, String> map = new HashMap<>();
        map.put("error code", String.valueOf(HttpStatus.BAD_REQUEST.value()));
        map.put("error message", e.getMessage());

        log.error(e.getMessage());
        return new ResponseEntity<>(map, headers, HttpStatus.BAD_REQUEST);
    }
}