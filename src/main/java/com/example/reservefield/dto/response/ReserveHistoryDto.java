package com.example.reservefield.dto.response;

import com.example.reservefield.domain.reserve.Status;

import java.time.LocalDate;

public record ReserveHistoryDto(
    Long id,
    Long userId,
    String userName,
    Long stadiumId,
    String stadiumName,
    Long reserveTimeId,
    String reserveTimeDescription,
    LocalDate reserveDate,
    Integer price,
    Status status
) {
}