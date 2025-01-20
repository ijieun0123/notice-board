package com.example.noticeboard.dto;

import org.springframework.web.multipart.MultipartFile;

public class UserDto {

    private Long userId; // 요청/응답용 ID
    private String username; // 사용자명
    private String password; // 비밀번호
    private MultipartFile profileImage; // 파일 업로드를 위한 필드

    // Getters and Setters
    public Long getId() {
        return userId;
    }

    public void setId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getUserId() {
        return userId;
    }

    public MultipartFile getProfileImage() {
        return profileImage;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setProfileImage(MultipartFile profileImage) {
        this.profileImage = profileImage;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

