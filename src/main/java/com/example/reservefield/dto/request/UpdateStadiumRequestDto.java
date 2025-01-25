package com.example.reservefield.dto.request;

import com.example.reservefield.domain.stadium.Address;
import com.example.reservefield.domain.stadium.DoorType;
import com.example.reservefield.domain.stadium.StadiumSize;
import com.example.reservefield.domain.stadium.StadiumType;
import jakarta.validation.constraints.Size;

import java.time.LocalTime;

public record UpdateStadiumRequestDto(
    @Size(max = 30, message = "구장 이름은 30글자 이상 될 수 없습니다.")
    String name,
    Integer sizeX,
    Integer sizeY,
    StadiumSize size,
    Integer price,
    DoorType doorType,
    StadiumType stadiumType,
    Long coverImage,
    Address address,
    @Size(max = 100, message = "주소는 100글자 이상 될 수 없습니다.")
    String detailAddress,
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
    @Size(max = 300, message = "상세 설명은 300글자 이상이 될 수 없습니다.")
    String intro,

    @Size(max = 300, message = "구장 내 규칙은 300글자 이상이 될 수 없습니다.")
    String promise
) {
}