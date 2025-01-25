package com.example.reservefield.dto.response;

import java.util.List;

public record StadiumInfosDto(
    List<StadiumInfoDto> AllStadium,
    Integer totalPages,
    Integer totalElements
) {
}