package com.example.reservefield.domain.stadium;

import com.example.reservefield.common.EnumType;
import lombok.Getter;

@Getter
public enum StadiumType implements EnumType {
    ARTIFICIAL_GRASS("인조잔디"),
    NATURAL_GRASS("천연잔디"),
    INDOOR("인도어");

    private final String description;

    StadiumType(String description) {
        this.description = description;
    }
}