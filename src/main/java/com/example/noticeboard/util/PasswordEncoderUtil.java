package com.example.noticeboard.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderUtil {
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String encode(String password) {
        String encodedPassword = passwordEncoder.encode(password);
        System.out.println("Encoded password: " + encodedPassword);
        return encodedPassword;
    }

    public static boolean matches(String rawPassword, String encodedPassword) {
        System.out.println("Raw password: " + rawPassword);
        System.out.println("Encoded password: " + encodedPassword);
        boolean isMatch = passwordEncoder.matches(rawPassword, encodedPassword);
        System.out.println("Password match result: " + isMatch);
        return isMatch;
    }
}

