package com.example.reservefield.mapper;

import com.example.reservefield.domain.review.Review;
import com.example.reservefield.dto.response.ReservationsReviewDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    @Mapping(target = "userId", source = "review.user.id")
    @Mapping(target = "stadiumId", source = "review.stadium.id")
    @Mapping(target = "stadiumName", source = "review.stadium.name")
    @Mapping(target = "reserveId", source = "review.reserve.id")
    @Mapping(target = "status", source = "review.reserve.status")
    @Mapping(target = "reserveTimeId", source = "review.reserveTime.id")
    @Mapping(target = "description", source = "review.reserveTime.description")
    ReservationsReviewDto toMyReserveReviewDto(Review review);
}