package com.example.noticeboard.service;

import com.example.noticeboard.dto.UserDto;
import com.example.noticeboard.entity.UserEntity;
import com.example.noticeboard.mapper.UserEntityModelMapper;
import com.example.noticeboard.mapper.UserModelDtoMapper;
import com.example.noticeboard.model.UserModel;
import com.example.noticeboard.repository.UserRepository;
import com.example.noticeboard.util.PasswordEncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;
    private final PasswordEncoderUtil passwordEncoderUtil;

    private final UserEntityModelMapper userEntityModelMapper;

    public UserService(UserRepository userRepository, PasswordEncoderUtil passwordEncoderUtil, UserEntityModelMapper userEntityModelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoderUtil = passwordEncoderUtil;
        this.userEntityModelMapper = userEntityModelMapper;
    }

    // 권한 확인
    public boolean hasRole(String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if (authority.getAuthority().equals("ROLE_" + role)) {
                    return true;
                }
            }
        }
        return false;
    }

    // 로그인 요청 처리
    public boolean authenticateUser(String username, String password) {

        // 사용자 정보 조회
        UserEntity userEntity = userRepository.findByUsername(username);

        // 사용자가 존재하고, 비밀번호가 일치하는지 확인
        if (userEntity != null && passwordEncoderUtil.matches(password, userEntity.getPassword())) {
            return true;  // 인증 성공
        }
        return false;  // 인증 실패
    }

    // 회원가입
    public UserModel registerUser(UserDto userDto) {
        System.out.println("UserService/registerUser - UserDto: " + userDto);

        // 비밀번호 암호화
        String encodedPassword = passwordEncoderUtil.encode(userDto.getPassword());

        // UserDto -> UserEntity 변환
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(encodedPassword);

        // 권한 설정 (예: "USER" 권한 부여)
        Set<String> authorities = new HashSet<>();
        authorities.add("ROLE_USER");  // "USER" 권한을 String으로 추가
        userEntity.setAuthorities(authorities);

        // 데이터베이스에 저장
        userEntity = userRepository.save(userEntity);

        System.out.println("UserService/registerUser - Saved UserEntity: " + userEntity);

        // UserEntity -> UserModel 변환 후 반환
        return userEntityModelMapper.toModel(userEntity);
    }

    // 사용자 조회
    public UserModel getUserById(Long id) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        UserEntity userEntity = optionalUser.get();
        return userEntityModelMapper.toModel(userEntity);
    }

    // 사용자 업데이트
//    public UserModel updateUser(Long id, UserDto userDto) {
//        Optional<UserEntity> optionalUser = userRepository.findById(id);
//        if (optionalUser.isEmpty()) {
//            throw new RuntimeException("User not found");
//        }
//        UserEntity userEntity = optionalUser.get();
//        userEntity.setUsername(userDto.getUsername());
//        userEntity.setPassword(userDto.getPassword());
//
//        UserEntity updatedEntity = userRepository.save(userEntity);
//        return userEntityModelMapper.toModel(updatedEntity);
//    }

    // 사용자 삭제
//    public void deleteUser(Long id) {
//        if (!userRepository.existsById(id)) {
//            throw new RuntimeException("User not found");
//        }
//        userRepository.deleteById(id);
//    }

    // UserModel → UserDto 변환
    public UserDto convertToDto(UserModel userModel) {
        return UserModelDtoMapper.toDto(userModel);
    }
}


