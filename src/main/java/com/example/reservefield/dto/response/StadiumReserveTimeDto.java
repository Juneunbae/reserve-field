package com.example.reservefield.dto.response;

import com.example.reservefield.domain.reserve.Status;

import java.time.LocalDate;

public record StadiumReserveTimeDto(
    Long stadiumId,
    LocalDate reserveDate,
    Long id,
    String name,
    String description,
    Status status
) {
}