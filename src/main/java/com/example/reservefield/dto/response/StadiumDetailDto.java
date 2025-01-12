package com.example.reservefield.dto.response;

import com.example.reservefield.domain.stadium.DoorType;
import com.example.reservefield.domain.stadium.StadiumSize;
import com.example.reservefield.domain.stadium.StadiumType;

import java.time.LocalTime;
import java.util.List;

public record StadiumDetailDto(
    Long id,
    Long adminId,
    String name,
    Integer sizeX,
    Integer sizeY,
    StadiumSize size,
    Boolean isUsed,
    Integer price,
    DoorType doorType,
    StadiumType stadiumType,
    Long coverImage,
    String address,
    LocalTime openingTime,
    LocalTime closingTime,
    Boolean isParking,
    Boolean isParkingFree,
    String parkingInfo,
    Boolean isShower,
    Boolean isShowerFree,
    String showerInfo,
    Boolean isWear,
    Boolean isWearFree,
    String wearInfo,
    Boolean isShoes,
    Boolean isShoesFree,
    String shoesInfo,
    Boolean isBall,
    String ballInfo,
    Boolean isToilet,
    String intro,
    String promise,
    List<ImageInfoDto> images
) {
}