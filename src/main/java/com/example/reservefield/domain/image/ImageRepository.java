package com.example.reservefield.domain.image;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByIdAndStadiumId(Long id, Long stadiumId);

    List<Image> findAllByStadiumId(Long stadiumId);
}