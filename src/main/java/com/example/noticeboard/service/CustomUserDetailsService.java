package com.example.noticeboard.service;

import com.example.noticeboard.entity.UserEntity;
import com.example.noticeboard.repository.UserRepository;
import com.example.noticeboard.security.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // authorities 필드를 SimpleGrantedAuthority로 변환
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (String authority : userEntity.getAuthorities()) {
            if (!authority.startsWith("ROLE_")) {
                authority = "ROLE_" + authority;
            }
            grantedAuthorities.add(new SimpleGrantedAuthority(authority));
        }

        // CustomUser로 반환
        return new CustomUser(userEntity.getUsername(), userEntity.getPassword(), grantedAuthorities, userEntity.getProfileImagePath());
    }
}
