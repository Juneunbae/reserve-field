package com.example.reservefield.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateMyInfoRequestDto(
    @NotBlank(message = "비밀번호는 필수 값입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]{8,256}$", message = "비밀번호는 8자리 이상 가능합니다.")
    String newPassword
) {
}