package com.example.noticeboard.mapper;

import com.example.noticeboard.entity.UserEntity;
import com.example.noticeboard.model.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserEntityModelMapper {

    public UserModel toModel(UserEntity entity) {
        UserModel model = new UserModel();
        model.setModelId(entity.getUserId());
        model.setModelUsername(entity.getUsername());
        model.setModelPassword(entity.getPassword());
        model.setProfileImagePath(entity.getProfileImagePath());
        return model;
    }

    public UserEntity toEntity(UserModel model) {
        UserEntity entity = new UserEntity();
        entity.setUserId(model.getModelId());
        entity.setUsername(model.getModelUsername());
        entity.setPassword(model.getModelPassword());
        entity.setProfileImagePath(model.getProfileImagePath());
        return entity;
    }
}

