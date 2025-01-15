package com.example.noticeboard.service;

import com.example.noticeboard.dto.UserDto;
import com.example.noticeboard.entity.UserEntity;
import com.example.noticeboard.mapper.UserEntityModelMapper;
import com.example.noticeboard.mapper.UserModelDtoMapper;
import com.example.noticeboard.model.UserModel;
import com.example.noticeboard.repository.UserRepository;
import com.example.noticeboard.util.PasswordEncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // 로그인 요청 처리
    public boolean authenticateUser(String username, String password) {
        System.out.println("UserService/authenticateUser - username: " + username);
        System.out.println("UserService/authenticateUser - password: " + password);

        // 사용자 정보 조회
        UserEntity userEntity = userRepository.findByUsername(username);

        // 사용자가 존재하고, 비밀번호가 일치하는지 확인
        if (userEntity != null && PasswordEncoderUtil.matches(password, userEntity.getPassword())) {
            System.out.println("Password matches: " + PasswordEncoderUtil.matches(password, userEntity.getPassword()));
            return true;  // 인증 성공
        }
        return false;  // 인증 실패
    }

    // 회원가입
    public UserModel registerUser(UserDto userDto) {
        System.out.println("UserService/registerUser - UserDto: " + userDto);

        // 비밀번호 암호화
        String encodedPassword = PasswordEncoderUtil.encode(userDto.getPassword());

        // UserDto -> UserEntity 변환
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(encodedPassword);
        // 필요한 필드들만 설정 (email 제거)

        // 데이터베이스에 저장
        userEntity = userRepository.save(userEntity);

        System.out.println("UserService/registerUser - Saved UserEntity: " + userEntity);

        // UserEntity -> UserModel 변환 후 반환
        return UserEntityModelMapper.toModel(userEntity);
    }

    // 사용자 조회
    public UserModel getUserById(Long id) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        UserEntity userEntity = optionalUser.get();
        return UserEntityModelMapper.toModel(userEntity);
    }

    // 사용자 업데이트
    public UserModel updateUser(Long id, UserDto userDto) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        UserEntity userEntity = optionalUser.get();
        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(userDto.getPassword());

        UserEntity updatedEntity = userRepository.save(userEntity);
        return UserEntityModelMapper.toModel(updatedEntity);
    }

    // 사용자 삭제
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

    // UserModel → UserDto 변환
    public UserDto convertToDto(UserModel userModel) {
        return UserModelDtoMapper.toDto(userModel);
    }
}

