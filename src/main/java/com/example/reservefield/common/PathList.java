package com.example.reservefield.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PathList {
    public static final String[] ADMIN_LIST = {
        // User

        // Stadium
        "/api/admin/stadiums",
        "/api/admin/stadiums/{stadiumId}",
        "/api/admin/stadiums/{id}/images",
        "/api/admin/stadiums/{id}/images/{imageId}",

        // Reserve
        "/api/admin/reservations",
        "/api/admin/reservations/{id}"
    };

    public static final String[] PLAYER_LIST = {
        // Reserve
        "/api/reservations",
        "/api/reservations/{id}/canceled",
        "/api/reservations/histories",
        "/api/reservations/histories/{id}"
    };
}