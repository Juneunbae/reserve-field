package com.example.reservefield.dto.response;

public record TokenInfoDto(
    String grantType,
    String accessToken,
    String refreshToken
) {
}