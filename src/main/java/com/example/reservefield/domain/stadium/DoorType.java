package com.example.reservefield.domain.stadium;

import com.example.reservefield.common.EnumType;
import lombok.Getter;

@Getter
public enum DoorType implements EnumType {
    INDOOR("INDOOR"),
    OUTDOOR("OUTDOOR");

    private final String description;

    DoorType(String description) {
        this.description = description;
    }
}