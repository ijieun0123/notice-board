package com.example.noticeboard.model;

public class UserModel {

    private Long userId; // 비즈니스 로직용 ID
    private String username; // 사용자명
    private String password; // 비밀번호

    // Getters and Setters
    public Long getModelId() {
        return userId;
    }

    public void setModelId(Long modelId) {
        this.userId = modelId;
    }

    public String getModelUsername() {
        return username;
    }

    public void setModelUsername(String modelUsername) {
        this.username = modelUsername;
    }

    public String getModelPassword() {
        return password;
    }

    public void setModelPassword(String modelPassword) {
        this.password = modelPassword;
    }

}

