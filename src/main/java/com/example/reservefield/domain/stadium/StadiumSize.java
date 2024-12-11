package com.example.reservefield.domain.stadium;

import com.example.reservefield.common.EnumType;
import lombok.Getter;

@Getter
public enum StadiumSize implements EnumType {
    THREE("3vs3"),
    FOUR("4vs4"),
    FIVE("5vs5"),
    SIX("6vs6"),
    EIGHT("8vs8");

    private final String description;

    StadiumSize(String description) {
        this.description = description;
    }
}