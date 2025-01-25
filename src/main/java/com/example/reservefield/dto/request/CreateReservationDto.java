package com.example.reservefield.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateReservationDto(
    @JsonProperty("stadiumId") Long stadiumId,

    @NotNull
    @FutureOrPresent(message = "지난 날짜는 예약할 수 없습니다.")
    @JsonProperty("reserveDate") LocalDate reserveDate,

    @JsonProperty("reserveTimeId") Long reserveTimeId
) {
}