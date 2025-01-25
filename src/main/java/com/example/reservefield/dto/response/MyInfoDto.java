package com.example.reservefield.dto.response;

import com.example.reservefield.domain.user.Role;

import java.time.LocalDateTime;

public record MyInfoDto(
    String email,
    String name,
    String phone,
    Role role,
    LocalDateTime created_at
) {
}