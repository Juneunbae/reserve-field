package com.example.reservefield.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
    @NotBlank(message = "빈 값이 될 수 없습니다.")
    @Email
    String email,
    @NotBlank(message = "빈 값이 될 수 없습니다.")
    String password
) {
}