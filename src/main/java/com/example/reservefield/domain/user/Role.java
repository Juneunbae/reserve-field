package com.example.reservefield.domain.user;

import com.example.reservefield.common.EnumType;
import lombok.Getter;

@Getter
public enum Role implements EnumType {
    PLAYER("PLAYER"),
    EMPLOYEE("EMPLOYEE"),
    ADMIN("ADMIN");

    private final String description;

    Role(String description) {
        this.description = description;
    }
}