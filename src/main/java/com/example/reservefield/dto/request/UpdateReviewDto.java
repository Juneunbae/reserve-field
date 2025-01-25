package com.example.reservefield.dto.request;

import com.example.reservefield.domain.review.Satisfaction;

public record UpdateReviewDto (
    Satisfaction satisfaction,
    String content
){
}