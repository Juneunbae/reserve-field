package com.example.reservefield.dto.request;

import com.example.reservefield.domain.stadium.Address;
import com.example.reservefield.domain.stadium.DoorType;
import com.example.reservefield.domain.stadium.StadiumSize;
import com.example.reservefield.domain.stadium.StadiumType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalTime;

public record CreateStadiumRequestDto(
    @NotBlank(message = "필수 값이므로 빈 칸이 될 수 없습니다.")
    @Size(max = 30, message = "구장 이름은 30글자 이상 될 수 없습니다.")
    @JsonProperty("name") String name,

    @NotNull(message = "필수 값이므로 빈 칸이 될 수 없습니다.")
    @JsonProperty("sizeX") Integer sizeX,

    @NotNull(message = "필수 값이므로 빈 칸이 될 수 없습니다.")
    @JsonProperty("sizeY") Integer sizeY,

    @NotNull(message = "필수 값이므로 빈 칸이 될 수 없습니다.")
    @JsonProperty("size") StadiumSize size,

    @NotNull(message = "필수 값이므로 빈 칸이 될 수 없습니다.")
    @JsonProperty("price") Integer price,

    @NotNull(message = "필수 값이므로 빈 칸이 될 수 없습니다.")
    @JsonProperty("doorType") DoorType doorType,

    @NotNull(message = "필수 값이므로 빈 칸이 될 수 없습니다.")
    @JsonProperty("stadiumType") StadiumType stadiumType,

    Long coverImage,

    @NotNull(message = "필수 값이므로 빈 칸이 될 수 없습니다.")
    @JsonProperty("address") Address address,

    @NotBlank(message = "필수 값이므로 빈 칸이 될 수 없습니다.")
    @Size(max = 100, message = "주소는 100글자 이상 될 수 없습니다.")
    @JsonProperty("detailAddress") String detailAddress,

    @NotNull(message = "필수 값이므로 빈 칸이 될 수 없습니다.")
    @JsonFormat(pattern = "HH:mm:ss")
    @JsonProperty("openingTime") LocalTime openingTime,

    @NotNull(message = "필수 값이므로 빈 칸이 될 수 없습니다.")
    @JsonFormat(pattern = "HH:mm:ss")
    @JsonProperty("closingTime") LocalTime closingTime,

    @NotNull(message = "필수 값이므로 빈 칸이 될 수 없습니다.")
    @JsonProperty("isParking") Boolean isParking,

    @NotNull(message = "필수 값이므로 빈 칸이 될 수 없습니다.")
    @JsonProperty("isParkingFree") Boolean isParkingFree,

    String parkingInfo,

    @NotNull(message = "필수 값이므로 빈 칸이 될 수 없습니다.")
    @JsonProperty("isShower") Boolean isShower,

    @NotNull(message = "필수 값이므로 빈 칸이 될 수 없습니다.")
    @JsonProperty("isShowerFree") Boolean isShowerFree,

    String showerInfo,

    @NotNull(message = "필수 값이므로 빈 칸이 될 수 없습니다.")
    @JsonProperty("isWear") Boolean isWear,

    @NotNull(message = "필수 값이므로 빈 칸이 될 수 없습니다.")
    @JsonProperty("isWearFree") Boolean isWearFree,

    String wearInfo,

    @NotNull(message = "필수 값이므로 빈 칸이 될 수 없습니다.")
    @JsonProperty("isShoes") Boolean isShoes,

    @NotNull(message = "필수 값이므로 빈 칸이 될 수 없습니다.")
    @JsonProperty("isShoesFree") Boolean isShoesFree,

    String shoesInfo,

    @NotNull(message = "필수 값이므로 빈 칸이 될 수 없습니다.")
    @JsonProperty("isBall") Boolean isBall,

    String ballInfo,

    @NotNull(message = "필수 값이므로 빈 칸이 될 수 없습니다.")
    @JsonProperty("isToilet") Boolean isToilet,

    @Size(max = 300, message = "상세 설명은 300글자 이상이 될 수 없습니다.")
    String intro,

    @Size(max = 300, message = "구장 내 규칙은 300글자 이상이 될 수 없습니다.")
    String promise
) {
}