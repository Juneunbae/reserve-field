package com.example.reservefield.dto.response;

import java.util.List;

public record StadiumReserveDto(
    List<StadiumReserveDetailDto> stadiums,
    Integer TotalElements,
    Integer TotalPages
) {
}