package com.example.reservefield.common.security;

import com.example.reservefield.common.EnumType;
import lombok.Getter;

@Getter
public enum JwtType implements EnumType {
    ACCESS("Access"),
    REFRESH("Refresh");

    private final String description;

    JwtType(String description) {
        this.description = description;
    }
}