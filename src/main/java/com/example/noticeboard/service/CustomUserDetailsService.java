package com.example.noticeboard.service;

import com.example.noticeboard.entity.UserEntity;
import com.example.noticeboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
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
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (String authority : userEntity.getAuthorities()) {
            authorities.add(new SimpleGrantedAuthority(authority)); // "ROLE_" 접두사 추가
        }

        return new User(userEntity.getUsername(), userEntity.getPassword(), authorities);
    }

}
