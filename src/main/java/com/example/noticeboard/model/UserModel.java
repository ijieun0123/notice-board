package com.example.noticeboard.model;

public class UserModel {
    private Long userId; // 비즈니스 로직용 ID
    private String username; // 사용자명
    private String password; // 비밀번호
    private String profileImagePath; // 프로필 이미지

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

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getProfileImagePath() {
        return profileImagePath;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }
}

