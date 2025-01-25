package com.example.reservefield.mapper;

import com.example.reservefield.domain.image.Image;
import com.example.reservefield.domain.reserve.ReserveTime;
import com.example.reservefield.domain.reserve.Status;
import com.example.reservefield.domain.stadium.Stadium;
import com.example.reservefield.dto.response.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.util.List;

@Mapper(componentModel = "spring")
public interface StadiumMapper {
    StadiumInfoDto toStadiumInfoDto(Stadium stadium);

    @Mapping(target = "AllStadium", source = "stadiumInfoDto")
    StadiumInfosDto toStadiumInfosDto(List<StadiumInfoDto> stadiumInfoDto, Integer totalPages, Integer totalElements);

    @Mapping(target = "sizeX", source = "stadium.sizeX")
    @Mapping(target = "sizeY", source = "stadium.sizeY")
    @Mapping(target = "adminId", source = "stadium.admin.id")
    StadiumDetailDto toStadiumDetailDto(Stadium stadium);

    @Mapping(target = "stadiumId", source = "image.stadium.id")
    ImageInfoDto toImageInfoDto(Image image);

    @Mapping(target = "name", source = "reserveTime.name")
    @Mapping(target = "description", source = "reserveTime.description")
    StadiumReserveTimeDto toStadiumReserveDto(Long stadiumId, LocalDate reserveDate, ReserveTime reserveTime, Status status);

    @Mapping(target = "adminId", source = "stadium.admin.id")
    StadiumReserveDetailDto toStadiumReserveDetailDto(Stadium stadium, List<ImageInfoDto> images, List<StadiumReserveTimeDto> reserveTimes);
}