package com.example.noticeboard.mapper;

import com.example.noticeboard.entity.UserEntity;
import com.example.noticeboard.model.UserModel;

public class UserEntityModelMapper {

    public static UserModel toModel(UserEntity entity) {
        UserModel model = new UserModel();
        model.setModelId(entity.getUserId());
        model.setModelUsername(entity.getUsername());
        model.setModelPassword(entity.getPassword());
        return model;
    }

    public static UserEntity toEntity(UserModel model) {
        UserEntity entity = new UserEntity();
        entity.setUserId(model.getModelId());
        entity.setUsername(model.getModelUsername());
        entity.setPassword(model.getModelPassword());
        return entity;
    }
}

