package com.example.reservefield.domain.review;

import com.example.reservefield.common.EnumType;
import lombok.Getter;

@Getter
public enum Satisfaction implements EnumType {
    GOOD("좋았어요"),
    BAD("별로였어요");

    private final String description;

    Satisfaction(String description) {
        this.description = description;
    }
}