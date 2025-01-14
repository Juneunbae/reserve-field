package com.example.reservefield.dto.response;

public record ImageInfoDto (
    Long id,
    Long stadiumId,
    String name,
    String path,
    Boolean isThumbnail
){
}