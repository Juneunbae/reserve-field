package com.example.reservefield.domain.reserve;

import com.example.reservefield.common.EnumType;
import lombok.Getter;

@Getter
public enum Status implements EnumType {
    AVAILABLE("예약 가능"),
    CONFIRM("예약 확정"),
    CANCELED("예약 취소");

    private final String description;

    Status(String description) {
        this.description = description;
    }
}