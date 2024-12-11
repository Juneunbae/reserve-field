package com.example.reservefield.dto.response;

import com.example.reservefield.domain.stadium.DoorType;
import com.example.reservefield.domain.stadium.StadiumSize;
import com.example.reservefield.domain.stadium.StadiumType;

import java.time.LocalTime;

public record StadiumDetailDto(
    Long id,
    Long adminId,
    String name,
    Integer xSize,
    Integer ySize,
    StadiumSize stadiumSize,
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
    String promise
) {
}