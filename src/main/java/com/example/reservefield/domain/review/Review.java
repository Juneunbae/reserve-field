package com.example.reservefield.domain.review;

import com.example.reservefield.domain.reserve.Reserve;
import com.example.reservefield.domain.reserve.ReserveTime;
import com.example.reservefield.domain.stadium.Stadium;
import com.example.reservefield.domain.user.User;
import com.example.reservefield.dto.request.UpdateReviewDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@ToString
@SuperBuilder
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Stadium stadium;

    @ManyToOne(fetch = FetchType.LAZY)
    private Reserve reserve;

    @Column(
        nullable = false
    )
    private LocalDate reserveDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private ReserveTime reserveTime;

    @Column(
        nullable = false,
        length = 10,
        columnDefinition = "varchar"
    )
    @Enumerated(EnumType.STRING)
    private Satisfaction satisfaction;

    @Column(
        length = 200
    )
    private String content;

    @CreatedDate
    private LocalDateTime createdAt;

    @CreatedBy
    private Long createdBy;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @LastModifiedBy
    private Long updatedBy;

    public void update(UpdateReviewDto update, User user) {
        if (update.satisfaction() != null) {
            this.satisfaction = update.satisfaction();
        }

        if (update.content() != null) {
            this.content = update.content();
        }

        this.updatedAt = LocalDateTime.now();
        this.updatedBy = user.getId();
    }
}