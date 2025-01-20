package com.example.noticeboard.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Set;

public class CustomUser extends User {
    private String profileImage;

    public CustomUser(String username, String password, Set<GrantedAuthority> authorities, String profileImage) {
        super(username, password, authorities);
        this.profileImage = profileImage;
    }

    public String getProfileImage() {
        return profileImage;
    }
}
