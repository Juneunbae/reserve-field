package com.example.reservefield.domain.stadium;

import com.example.reservefield.domain.user.User;
import com.example.reservefield.dto.request.UpdateStadiumRequestDto;
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

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Entity
@ToString
@SuperBuilder
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stadium {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User admin;

    @Column(
        nullable = false,
        length = 30
    )
    private String name;

    @Column(
        nullable = false
    )
    private Integer xSize;

    @Column(
        nullable = false
    )
    private Integer ySize;

    @Column(
        nullable = false,
        columnDefinition = "varchar"
    )
    @Enumerated(EnumType.STRING)
    private StadiumSize size;

    @Column(
        nullable = false
    )
    private Boolean isUsed;

    @Column(
        nullable = false
    )
    private Integer price;

    @Column(
        nullable = false,
        columnDefinition = "varchar"
    )
    @Enumerated(EnumType.STRING)
    private DoorType doorType;

    @Column(
        nullable = false,
        columnDefinition = "varchar"
    )
    @Enumerated(EnumType.STRING)
    private StadiumType stadiumType;

    private Long coverImage;

    @Column(
        nullable = false,
        columnDefinition = "varchar"
    )
    @Enumerated(EnumType.STRING)
    private Address address;

    @Column(
        nullable = false,
        length = 100
    )
    private String detailAddress;

    @Column(
        nullable = false
    )
    private LocalTime openingTime;

    @Column(
        nullable = false
    )
    private LocalTime closingTime;

    @Column(
        nullable = false
    )
    private Boolean isParking;

    @Column(
        nullable = false
    )
    private Boolean isParkingFree;

    @Column(
        length = 100
    )
    private String parkingInfo;

    @Column(
        nullable = false
    )
    private Boolean isShower;

    @Column(
        nullable = false
    )
    private Boolean isShowerFree;

    @Column(
        length = 100
    )
    private String showerInfo;

    @Column(
        nullable = false
    )
    private Boolean isWear;

    @Column(
        nullable = false
    )
    private Boolean isWearFree;

    @Column(
        length = 100
    )
    private String wearInfo;

    @Column(
        nullable = false
    )
    private Boolean isShoes;

    @Column(
        nullable = false
    )
    private Boolean isShoesFree;

    @Column(
        length = 100
    )
    private String shoesInfo;

    @Column(
        nullable = false
    )
    private Boolean isBall;

    @Column(
        length = 100
    )
    private String ballInfo;

    @Column(
        nullable = false
    )
    private Boolean isToilet;

    @Column(
        length = 300
    )
    private String intro;

    @Column(
        length = 300
    )
    private String promise;

    @CreatedDate
    private LocalDateTime createdAt;

    @CreatedBy
    private Long createdBy;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @LastModifiedBy
    private Long updatedBy;

    public void update(UpdateStadiumRequestDto update) {
        if (update.name() != null) {
            this.name = update.name();
        }

        if (update.xSize() != null) {
            this.xSize = update.xSize();
        }

        if (update.ySize() != null) {
            this.ySize = update.ySize();
        }

        if (update.size() != null) {
            this.size = update.size();
        }

        if (update.price() != null) {
            this.price = update.price();
        }

        if (update.doorType() != null) {
            this.doorType = update.doorType();
        }

        if (update.stadiumType() != null) {
            this.stadiumType = update.stadiumType();
        }

        if (update.coverImage() != null) {
            this.coverImage = update.coverImage();
        }

        if (update.address() != null) {
            this.address = update.address();
        }

        if (update.detailAddress() != null) {
            this.detailAddress = update.detailAddress();
        }

        if (update.openingTime() != null) {
            this.openingTime = update.openingTime();
        }

        if (update.closingTime() != null) {
            this.closingTime = update.closingTime();
        }

        if (update.isParking() != null) {
            this.isParking = update.isParking();
        }

        if (update.isParkingFree() != null) {
            this.isParkingFree = update.isParkingFree();
        }

        if (update.parkingInfo() != null) {
            this.parkingInfo = update.parkingInfo();
        }

        if (update.isShower() != null) {
            this.isShower = update.isShower();
        }
        if (update.isShowerFree() != null) {
            this.isShowerFree = update.isShowerFree();
        }

        if (update.showerInfo() != null) {
            this.showerInfo = update.showerInfo();
        }

        if (update.isWear() != null) {
            this.isWear = update.isWear();
        }

        if (update.isWearFree() != null) {
            this.isWearFree = update.isWearFree();
        }

        if (update.wearInfo() != null) {
            this.wearInfo = update.wearInfo();
        }

        if (update.isShoes() != null) {
            this.isShoes = update.isShoes();
        }

        if (update.isShoesFree() != null) {
            this.isShoesFree = update.isShoesFree();
        }

        if (update.shoesInfo() != null) {
            this.shoesInfo = update.shoesInfo();
        }

        if (update.isBall() != null) {
            this.isBall = update.isBall();
        }

        if (update.ballInfo() != null) {
            this.ballInfo = update.ballInfo();
        }

        if (update.isToilet() != null) {
            this.isToilet = update.isToilet();
        }

        if (update.intro() != null) {
            this.intro = update.intro();
        }

        if (update.promise() != null) {
            this.promise = update.promise();
        }

        this.updatedBy = 1L;
        this.updatedAt = LocalDateTime.now();
    }
}