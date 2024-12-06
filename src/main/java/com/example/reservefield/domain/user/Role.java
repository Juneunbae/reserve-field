package com.example.reservefield.domain.user;

import com.example.reservefield.common.EnumType;
import lombok.Getter;

@Getter
public enum Role implements EnumType {
    PLAYER("사용자"),
    EMPLOYEE("직원"),
    ADMIN("관리자");

    private final String description;

    Role(String description) {
        this.description = description;
    }
}