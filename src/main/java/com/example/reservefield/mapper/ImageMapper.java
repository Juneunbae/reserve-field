package com.example.reservefield.mapper;

import com.example.reservefield.domain.image.Image;
import com.example.reservefield.domain.stadium.Stadium;
import com.example.reservefield.dto.response.ImageInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface ImageMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "stadium", source = "stadium")
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "name", source = "fileName")
    Image toImageEntity(Stadium stadium, String fileName, String path, Boolean isThumbnail, LocalDateTime createdAt);

    @Mapping(target = "stadiumId", source = "image.stadium.id")
    ImageInfoDto toImageInfoDto(Image image);
}