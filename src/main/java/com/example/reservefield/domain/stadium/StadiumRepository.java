package com.example.reservefield.domain.stadium;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StadiumRepository extends JpaRepository<Stadium, Long> {
    @Query(
        "SELECT s " +
            "FROM Stadium s " +
            "WHERE s.isUsed IS TRUE " +
            "AND s.name LIKE CONCAT('%', :name, '%') " +
            "AND (:address IS NULL OR s.address = :address) " +
            "AND (:stadiumSize IS NULL OR s.size = :stadiumSize) " +
            "AND (:stadiumType IS NULL OR s.stadiumType = :stadiumType)"
    )
        // TODO: 예약 시간 및 날짜 조건 추가하기
    List<Stadium> findAllStadiumList(
        Pageable pageable,
        @Param("name") String name,
        @Param("address") Address address,
        @Param("stadiumSize") StadiumSize stadiumSize,
        @Param("stadiumType") StadiumType stadiumType
    );

    @Query(
        "SELECT count(s) " +
            "FROM Stadium s " +
            "WHERE s.isUsed is True " +
            "AND s.name LIKE CONCAT('%', :name, '%') " +
            "AND (:address IS NULL OR s.address = :address) " +
            "AND (:stadiumSize IS NULL OR s.size = :stadiumSize) " +
            "AND (:stadiumType IS NULL OR s.stadiumType = :stadiumType)"
    )
        // TODO: 예약 시간 및 날짜 조건 추가하기
    Integer countAllStadiumList(
        @Param("name") String name,
        @Param("address") Address address,
        @Param("stadiumSize") StadiumSize stadiumSize,
        @Param("stadiumType") StadiumType stadiumType
    );
}