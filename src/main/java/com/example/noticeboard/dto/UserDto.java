package com.example.noticeboard.dto;

public class UserDto {

    private Long userId; // 요청/응답용 ID
    private String username; // 사용자명
    private String password; // 비밀번호

    // Getters and Setters
    public Long getId() {
        return userId;
    }

    public void setId(Long dtoId) {
        this.userId = dtoId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String dtoUsername) {
        this.username = dtoUsername;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String dtoPassword) {
        this.password = dtoPassword;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

