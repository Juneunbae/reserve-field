package com.example.reservefield.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
@Slf4j
@RequiredArgsConstructor
public class ValidationErrors {
    public void checkDtoErrors(Errors errors, String message) {
        if (errors.hasErrors()) {
            for (var error : errors.getFieldErrors()) {
                log.error("Field error in object '{}': field='{}', rejected value='{}', message='{}'",
                    error.getObjectName(), error.getField(), error.getRejectedValue(), error.getDefaultMessage());
            }

            throw new CustomException(HttpStatus.BAD_REQUEST, message); // TODO: message 필요한지 검토
        }
    }
}