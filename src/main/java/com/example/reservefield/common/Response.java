package com.example.reservefield.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Response {
    public static ResponseEntity<Map<String, String>> ok(String message) {
        Map<String, String> map = new HashMap<>();

        map.put("status code", String.valueOf(HttpStatus.OK.value()));
        map.put("message", message);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    public static ResponseEntity<Map<String, Object>> ok(Object data) {
        Map<String, Object> map = new HashMap<>();

        map.put("status code", String.valueOf(HttpStatus.OK.value()));
        map.put("data", data);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}