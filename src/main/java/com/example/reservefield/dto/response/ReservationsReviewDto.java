package com.example.reservefield.dto.response;

import com.example.reservefield.domain.reserve.Status;
import com.example.reservefield.domain.review.Satisfaction;

import java.time.LocalDate;

public record ReservationsReviewDto(
    Long id,
    Long userId,
    Long stadiumId,
    String stadiumName,
    Long reserveId,
    Status status,
    LocalDate reserveDate,
    Long reserveTimeId,
    String description,
    Satisfaction satisfaction,
    String content
) {
}