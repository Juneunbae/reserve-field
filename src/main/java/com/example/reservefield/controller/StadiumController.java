package com.example.reservefield.controller;

import com.example.reservefield.domain.stadium.Address;
import com.example.reservefield.domain.stadium.StadiumService;
import com.example.reservefield.domain.stadium.StadiumSize;
import com.example.reservefield.domain.stadium.StadiumType;
import com.example.reservefield.dto.request.CreateStadiumRequestDto;
import com.example.reservefield.dto.request.UpdateStadiumRequestDto;
import com.example.reservefield.dto.response.StadiumDetailDto;
import com.example.reservefield.dto.response.StadiumInfosDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stadiums")
public class StadiumController {
    private final StadiumService stadiumService;

    @Description(
        "전체 구장 정보 가져오기 - Paging"
    )
    @GetMapping
    public ResponseEntity<StadiumInfosDto> getStadiumInfos(
        @RequestParam(required = false, defaultValue = "") String name,
        @RequestParam(required = false, defaultValue = "") Address address,
        @RequestParam(required = false, defaultValue = "") StadiumSize stadiumSize,
        @RequestParam(required = false, defaultValue = "") StadiumType stadiumType,
        @RequestParam(required = false, defaultValue = "1") int page
    ) {
        return ResponseEntity.ok(
            stadiumService.getStadiumInfos(
                name, address, stadiumSize, stadiumType, page
            )
        );
    }

    @Description(
        "구장 정보 가져오기 - 단일"
    )
    @GetMapping("/{id}")
    public ResponseEntity<StadiumDetailDto> getStadiumDetailInfo(@PathVariable Long id) {
        return ResponseEntity.ok(stadiumService.getStadiumDetailInfo(id));
    }

    @Description(
        "구장 정보 등록하기"
    )
    @PostMapping("/create")
    public ResponseEntity<Void> createStadium(
        @Validated @RequestBody List<CreateStadiumRequestDto> create,
        Errors errors
    ) {
        stadiumService.createStadium(create, errors);
        return ResponseEntity.ok().build();
    }

    @Description(
        "구장 정보 수정하기"
    )
    @PutMapping("/{stadiumId}/update")
    public ResponseEntity<Void> updateStadium(
        @PathVariable Long stadiumId,
        @Validated @RequestBody UpdateStadiumRequestDto update,
        Errors errors
    ) {
        stadiumService.updateStadium(stadiumId, update, errors);
        return ResponseEntity.ok().build();
    }

    @Description(
        "구장 정보 삭제하기"
    )
    @DeleteMapping("/{stadiumId}/delete")
    public ResponseEntity<Void> deleteStadium(@PathVariable Long stadiumId) {
        stadiumService.deleteStadium(stadiumId);
        return ResponseEntity.ok().build();
    }
}