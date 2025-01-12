package com.example.reservefield.dto.response;

import com.example.reservefield.domain.stadium.Address;
import com.example.reservefield.domain.stadium.DoorType;
import com.example.reservefield.domain.stadium.StadiumSize;

public record StadiumInfoDto(
    String name,
    Integer sizeX,
    Integer sizeY,
    StadiumSize size,
    Address address,
    Integer price,
    DoorType doorType,
    Boolean isParking,
    Boolean isShower,
    Boolean isWear,
    Boolean isShoes,
    Boolean isBall,
    Boolean isToilet
) {
}