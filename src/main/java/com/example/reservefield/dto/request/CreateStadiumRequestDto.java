package com.example.reservefield.dto.request;

import com.example.reservefield.domain.stadium.Address;
import com.example.reservefield.domain.stadium.DoorType;
import com.example.reservefield.domain.stadium.StadiumSize;
import com.example.reservefield.domain.stadium.StadiumType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalTime;

public record CreateStadiumRequestDto(
    Long adminId,

    @NotBlank(message = "필수 값이므로 빈 칸이 될 수 없습니다.")
    @Size(max = 30, message = "구장 이름은 30글자 이상 될 수 없습니다.")
    String name,

    @NotNull(message = "필수 값이므로 빈 칸이 될 수 없습니다.")
    Integer xSize,

    @NotNull(message = "필수 값이므로 빈 칸이 될 수 없습니다.")
    Integer ySize,

    @NotNull(message = "필수 값이므로 빈 칸이 될 수 없습니다.")
    StadiumSize size,

    @NotNull(message = "필수 값이므로 빈 칸이 될 수 없습니다.")
    Integer price,

    @NotNull(message = "필수 값이므로 빈 칸이 될 수 없습니다.")
    DoorType doorType,

    @NotNull(message = "필수 값이므로 빈 칸이 될 수 없습니다.")
    StadiumType stadiumType,

    Long coverImage,

    @NotNull(message = "필수 값이므로 빈 칸이 될 수 없습니다.")
    Address address,

    @NotBlank(message = "필수 값이므로 빈 칸이 될 수 없습니다.")
    @Size(max = 100, message = "주소는 100글자 이상 될 수 없습니다.")
    String detailAddress,

    @NotNull(message = "필수 값이므로 빈 칸이 될 수 없습니다.")
    LocalTime openingTime,

    @NotNull(message = "필수 값이므로 빈 칸이 될 수 없습니다.")
    LocalTime closingTime,

    @NotNull(message = "필수 값이므로 빈 칸이 될 수 없습니다.")
    Boolean isParking,

    @NotNull(message = "필수 값이므로 빈 칸이 될 수 없습니다.")
    Boolean isParkingFree,

    String parkingInfo,

    @NotNull(message = "필수 값이므로 빈 칸이 될 수 없습니다.")
    Boolean isShower,

    @NotNull(message = "필수 값이므로 빈 칸이 될 수 없습니다.")
    Boolean isShowerFree,

    String showerInfo,

    @NotNull(message = "필수 값이므로 빈 칸이 될 수 없습니다.")
    Boolean isWear,

    @NotNull(message = "필수 값이므로 빈 칸이 될 수 없습니다.")
    Boolean isWearFree,

    String wearInfo,

    @NotNull(message = "필수 값이므로 빈 칸이 될 수 없습니다.")
    Boolean isShoes,

    @NotNull(message = "필수 값이므로 빈 칸이 될 수 없습니다.")
    Boolean isShoesFree,

    String shoesInfo,

    @NotNull(message = "필수 값이므로 빈 칸이 될 수 없습니다.")
    Boolean isBall,

    String ballInfo,

    @NotNull(message = "필수 값이므로 빈 칸이 될 수 없습니다.")
    Boolean isToilet,

    @Size(max = 300, message = "상세 설명은 300글자 이상이 될 수 없습니다.")
    String intro,

    @Size(max = 300, message = "구장 내 규칙은 300글자 이상이 될 수 없습니다.")
    String promise
) {
}