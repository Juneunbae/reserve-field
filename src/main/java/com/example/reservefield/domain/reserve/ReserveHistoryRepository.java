package com.example.reservefield.domain.reserve;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReserveHistoryRepository extends JpaRepository<ReserveHistory, Long> {
    List<ReserveHistory> findAllByUserId(Long userId);
}