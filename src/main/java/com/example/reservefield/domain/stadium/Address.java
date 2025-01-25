package com.example.reservefield.domain.stadium;

import com.example.reservefield.common.EnumType;
import lombok.Getter;

@Getter
public enum Address implements EnumType {
    SEOUL("서울"),
    GYEONGGI("경기도"),
    GANGWON("강원도"),
    INCHEON("인천"),
    DAEJEON("대전/세종"),
    CHUNG_NAM("충남"),
    CHUNG_BUK("충북"),
    DAEGU("대구"),
    GYEONGBUK("경북"),
    GYEONGNAM("경남"),
    BUSAN("부산"),
    ULSAN("울산"),
    GWANGJU("광주"),
    JEONNAM("전남"),
    JEONBUK("전북"),
    JEJU("제주");

    private final String description;

    Address(String description) {
        this.description = description;
    }
}