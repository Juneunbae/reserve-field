package com.example.reservefield.dto.request;

import com.example.reservefield.domain.review.Satisfaction;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record CreateReviewDto(
    @JsonProperty("stadiumId") Long stadiumId,
    @JsonProperty("reserveId") Long reserveId,
    @JsonProperty("reserveDate") LocalDate reserveDate,
    @JsonProperty("reserveTimeId") Long reserveTimeId,
    @JsonProperty("satisfaction") Satisfaction satisfaction,
    @JsonProperty("content") String content
) {
}