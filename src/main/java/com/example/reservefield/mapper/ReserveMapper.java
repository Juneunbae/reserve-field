package com.example.reservefield.mapper;

import com.example.reservefield.domain.reserve.Reserve;
import com.example.reservefield.domain.reserve.ReserveHistory;
import com.example.reservefield.domain.reserve.ReserveTime;
import com.example.reservefield.dto.response.ReserveHistoryDto;
import com.example.reservefield.dto.response.ReserveInfoDto;
import com.example.reservefield.dto.response.ReserveTimeInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReserveMapper {
    @Mapping(target = "stadiumId", source = "reserve.stadium.id")
    @Mapping(target = "reserveTime", source = "reserve.reserveTime")
    ReserveInfoDto toReserveInfoDto(Reserve reserve);

    ReserveTimeInfoDto toReserveTimeInfoDto(ReserveTime reserveTime);

    ReserveHistoryDto toReserveHistoryDto(ReserveHistory reserveHistory);
}