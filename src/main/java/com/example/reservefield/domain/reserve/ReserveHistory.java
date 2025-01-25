package com.example.reservefield.domain.reserve;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@ToString
@SuperBuilder
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReserveHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(
        length = 50,
        nullable = false
    )
    private String userName;

    @Column(nullable = false)
    private Long stadiumId;

    @Column(
        length = 30,
        nullable = false
    )
    private String stadiumName;

    @Column(nullable = false)
    private Long reserveTimeId;

    @Column(
        length = 20,
        nullable = false
    )
    private String reserveTimeDescription;

    @Column(nullable = false)
    private LocalDate reserveDate;

    @Column(nullable = false)
    private Integer price;

    @Column(
        nullable = false,
        length = 10
    )
    private String status;

    private LocalDateTime createdAt;
}