package com.example.reservefield.dto.response;

import com.example.reservefield.domain.reserve.Status;

import java.time.LocalDate;

public record ReserveInfoDto(
    Long id,
    Long stadiumId,
    LocalDate reserveDate,
    Status status,
    ReserveTimeInfoDto reserveTime
) {
}