package com.example.noticeboard.service;

import com.example.noticeboard.model.UserModel;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService implements org.springframework.security.core.userdetails.UserDetailsService {

    private Map<String, UserModel> userDatabase = new HashMap<>();
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // 사용자 정보 저장
    public void saveUser(UserModel user) {
        userDatabase.put(user.getUsername(), user);
    }

    // 사용자 정보 조회
    public UserModel getUser(String username) {
        return userDatabase.get(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel userModel = userDatabase.get(username);
        if (userModel == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return User.builder()
                .username(userModel.getUsername())
                .password(userModel.getPassword())
                .roles("USER")  // 기본 권한 설정
                .build();
    }

    // 비밀번호 검증
    public boolean checkPassword(String username, String rawPassword) {
        UserModel user = getUser(username);
        if (user != null) {
            return passwordEncoder.matches(rawPassword, user.getPassword());
        }
        return false;
    }
}
