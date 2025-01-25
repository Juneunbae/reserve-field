package com.example.reservefield.controller;

import com.example.reservefield.domain.stadium.*;
import com.example.reservefield.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stadiums")
public class StadiumController {
    private final StadiumService stadiumService;

    @Description(
        "전체 구장 정보 가져오기 - Paging (임직원 용)"
    )
    @GetMapping
    public ResponseEntity<StadiumInfosDto> getStadiumInfos(
        @RequestParam(required = false, defaultValue = "1") int page,
        @RequestParam(required = false, defaultValue = "") String name,
        @RequestParam(required = false, defaultValue = "") Address address,
        @RequestParam(required = false, defaultValue = "") StadiumSize stadiumSize,
        @RequestParam(required = false, defaultValue = "") StadiumType stadiumType,
        @RequestParam(required = false, defaultValue = "") DoorType doorType
    ) {
        return ResponseEntity.ok(stadiumService.getStadiumInfos(name, address, stadiumSize, stadiumType, page, doorType));
    }

    @Description(
        "구장 정보 가져오기 - 단일"
    )
    @GetMapping("/{id}")
    public ResponseEntity<StadiumDetailDto> getStadiumDetailInfo(@PathVariable Long id) {
        return ResponseEntity.ok(stadiumService.getStadiumDetailInfo(id));
    }

    @Description(
        "구장 이미지 조회"
    )
    @GetMapping("/{id}/images")
    public ResponseEntity<List<ImageInfoDto>> getStadiumImages(@PathVariable Long id) {
        return ResponseEntity.ok(stadiumService.getStadiumImages(id));
    }

    @Description(
        "특정 구장 예약정보 조회하기"
    )
    @GetMapping("/{id}/reservation")
    public ResponseEntity<List<StadiumReserveTimeDto>> getStadiumReserveTime(
        @PathVariable("id") Long id,
        @RequestParam(required = false, defaultValue = "") LocalDate reserveDate
    ) {
        return ResponseEntity.ok(stadiumService.getStadiumReserveTime(id, reserveDate));
    }

    @Description(
        "전체 구장 정보 및 예약정보 조회하기"
    )
    @GetMapping("/reservations")
    public ResponseEntity<StadiumReserveDto> getStadiumReserve(
        @RequestParam(required = false, defaultValue = "1") int page,
        @RequestParam(required = false, defaultValue = "") LocalDate reserveDate,
        @RequestParam(required = false, defaultValue = "") String name,
        @RequestParam(required = false, defaultValue = "") Address address,
        @RequestParam(required = false, defaultValue = "") StadiumSize stadiumSize,
        @RequestParam(required = false, defaultValue = "") StadiumType stadiumType,
        @RequestParam(required = false, defaultValue = "") DoorType doorType
    ) {
        return ResponseEntity.ok(stadiumService.getStadiumReserve(page, reserveDate, name, address, stadiumSize, stadiumType, doorType));
    }
}