package com.example.reservefield.mapper;

import com.example.reservefield.domain.user.User;
import com.example.reservefield.dto.response.MyInfoDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    MyInfoDto toMyInfoDto(User user);
}