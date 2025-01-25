package com.example.reservefield.domain.reserve;

import com.example.reservefield.domain.stadium.Stadium;
import com.example.reservefield.domain.user.User;
import com.example.reservefield.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReserveHistoryService {
    private final ReserveHistoryRepository reserveHistoryRepository;

    public void createHistory(User user, Stadium stadium, ReserveTime reserveTime, LocalDate reserveDate, Status status) {
        ReserveHistory reserveHistory = ReserveHistory.builder()
            .userId(user.getId())
            .userName(user.getName())
            .stadiumId(stadium.getId())
            .stadiumName(stadium.getName())
            .reserveTimeId(reserveTime.getId())
            .reserveTimeDescription(reserveTime.getDescription())
            .reserveDate(reserveDate)
            .price(stadium.getPrice())
            .status(status.name())
            .createdAt(LocalDateTime.now())
            .build();

        reserveHistoryRepository.save(reserveHistory);
    }

    public List<ReserveHistory> getReserveHistoriesByUserId(Long userId) {
        return reserveHistoryRepository.findAllByUserId(userId);
    }

    public ReserveHistory getReserveHistoryById(Long id) {
        return reserveHistoryRepository.findById(id)
            .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 예약 내역 정보입니다."));
    }
}