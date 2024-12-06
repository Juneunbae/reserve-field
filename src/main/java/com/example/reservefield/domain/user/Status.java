package com.example.reservefield.domain.user;

import com.example.reservefield.common.EnumType;
import lombok.Getter;

@Getter
public enum Status implements EnumType {
    WAITING("인증 대기 중"),
    COMPLETE("인증 완료"),
    STOPPED("정지");

    private final String description;

    Status(String description) {
        this.description = description;
    }
}