package com.example.reservefield.controller;

import com.example.reservefield.domain.reserve.ReserveService;
import com.example.reservefield.dto.request.CreateReservationDto;
import com.example.reservefield.dto.response.ReserveHistoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
public class ReserveController {
    private final ReserveService reserveService;

    @Description(
        "예약하기"
    )
    @PostMapping
    public ResponseEntity<Void> createReserve(@Validated @RequestBody CreateReservationDto create, Errors errors) {
        reserveService.createReserve(create, errors);
        return ResponseEntity.ok().build();
    }

    @Description(
        "예약 취소하기"
    )
    @DeleteMapping("/{id}/canceled")
    public ResponseEntity<Void> cancelReserve(@PathVariable("id") Long id) {
        reserveService.cancelReserve(id);
        return ResponseEntity.ok().build();
    }

    @Description(
        "사용자별 신청 내역 전체 조회"
    )
    @GetMapping("/histories")
    public ResponseEntity<List<ReserveHistoryDto>> getReservationHistories() {
        return ResponseEntity.ok(reserveService.getReservationHistories());
    }

    @Description(
        "사용자별 신청 내역 단일 조회"
    )
    @GetMapping("/histories/{id}")
    public ResponseEntity<ReserveHistoryDto> getReservationHistory(
        @PathVariable Long id
    ) {
        return ResponseEntity.ok(reserveService.getReservationHistory(id));
    }
}