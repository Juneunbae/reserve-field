package com.example.reservefield.domain.review;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Boolean existsByReserveIdAndReserveTimeIdAndReserveDate(Long reserveId, Long reserveTimeId, LocalDate reserveDate);

    List<Review> findAllByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

    @Query(
        "SELECT r " +
            "FROM Review r " +
            "LEFT JOIN r.stadium s " +
            "WHERE s.admin.id = :adminId " +
            "ORDER BY r.createdAt DESC"
    )
    List<Review> findAllByAdminId(@Param("adminId") Long adminId, Pageable pageable);

    @Query(
        "SELECT r " +
            "FROM Review r " +
            "LEFT JOIN r.stadium s " +
            "WHERE r.id = :reviewId "
    )
    Optional<Review> findByReviewId(@Param("reviewId") Long id);
}