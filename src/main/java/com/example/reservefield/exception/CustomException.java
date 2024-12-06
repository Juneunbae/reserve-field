package com.example.reservefield.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

@Getter
public class CustomException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final StackTraceElement location;

    public CustomException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.location = Arrays.stream(Thread.currentThread().getStackTrace())
            .filter(e -> e.getClassName().startsWith("com.example"))
            .skip(2)
            .findFirst()
            .orElse(null);
    }
}