package com.example.reservefield.domain.reserve;

import com.example.reservefield.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReserveTimeService {
    private final ReserveTimeRepository reserveTimeRepository;

    public ReserveTime getReserveTimeById(Long id) {
        return reserveTimeRepository.findById(id)
            .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 예약 시간대입니다."));
    }
}