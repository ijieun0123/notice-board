package com.example.noticeboard.mapper;

import com.example.noticeboard.dto.UserDto;
import com.example.noticeboard.model.UserModel;

public class UserModelDtoMapper {

    public static UserDto toDto(UserModel model) {
        UserDto dto = new UserDto();
        dto.setId(model.getModelId());
        dto.setUsername(model.getModelUsername());
        dto.setPassword(model.getModelPassword());
        return dto;
    }

    public static UserModel toModel(UserDto dto) {
        UserModel model = new UserModel();
        model.setModelId(dto.getId());
        model.setModelUsername(dto.getUsername());
        model.setModelPassword(dto.getPassword());
        return model;
    }
}

