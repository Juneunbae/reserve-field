package com.example.reservefield.domain.reserve;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReserveRepository extends JpaRepository<Reserve, Long> {
    Boolean existsByStadiumIdAndReserveDateAndReserveTimeIdAndStatus(Long stadiumId, LocalDate reserveDate, Long reserveTimeId, Status status);

    @Query(
        "SELECT r " +
            "FROM Reserve r " +
            "LEFT JOIN r.stadium s " +
            "WHERE s.admin.id = :adminId"
    )
    List<Reserve> findAllByAdminId(@Param("adminId") Long adminId);

    @Query(
        "SELECT rt " +
            "FROM ReserveTime rt " +
            "LEFT JOIN Reserve r " +
            "ON r.reserveTime.id = rt.id " +
            "AND r.stadium.id = :stadiumId " +
            "AND r.reserveDate = :reserveDate " +
            "WHERE r.id IS NULL"
    )
    List<ReserveTime> findAllByAvailableReserve(
        @Param("stadiumId") Long stadiumId,
        @Param("reserveDate") LocalDate reserveDate
    );

    @Query(
        "SELECT EXISTS ( " +
            "SELECT r " +
            "FROM Reserve r " +
            "WHERE r.reserveDate >= :today " +
            "AND r.stadium.id = :stadiumId " +
            ")"
    )
    Boolean existsByTodayReserve(@Param("today") LocalDate today, @Param("stadiumId") Long stadiumId);
}