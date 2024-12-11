package com.example.reservefield.mapper;

import com.example.reservefield.domain.stadium.Stadium;
import com.example.reservefield.dto.response.StadiumDetailDto;
import com.example.reservefield.dto.response.StadiumInfoDto;
import com.example.reservefield.dto.response.StadiumInfosDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StadiumMapper {
    StadiumInfoDto toStadiumInfoDto(Stadium stadium);

    @Mapping(target = "AllStadium", source = "stadiumInfoDto")
    StadiumInfosDto toStadiumInfosDto(List<StadiumInfoDto> stadiumInfoDto, Integer totalPages, Integer totalElements);

    StadiumDetailDto toStadiumDetailDto(Stadium stadium);
}