package com.example.reservefield.domain.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Entity
@ToString
@SuperBuilder
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
        nullable = false,
        unique = true,
        length = 100
    )
    private String email;

    @Column(
        nullable = false
    )
    private String password;

    @Column(
        nullable = false,
        length = 30
    )
    private String name;

    @Column(
        nullable = false,
        length = 20
    )
    private String phone;

    @Column(
        nullable = false,
        length = 10,
        columnDefinition = "varchar"
    )
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(
        nullable = false,
        length = 10,
        columnDefinition = "varchar"
    )
    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime logged_in_at;

    @Column(
        columnDefinition = "0"
    )
    private Integer login_fail_count;

    private LocalDateTime password_changed_at;

    @CreatedDate
    private LocalDateTime created_at;

    @LastModifiedDate
    private LocalDateTime updated_at;

    public void changePassword(String newPassword) {
        this.password = newPassword;
        this.password_changed_at = LocalDateTime.now();
    }

    public void successLogin() {
        this.login_fail_count = 0;
        this.logged_in_at = LocalDateTime.now();
    }

    public void failLogin() {
        this.login_fail_count++;
    }

    public void updateStatus(Status status) {
        this.status = status;
    }
}