package com.example.noticeboard.service;

import com.example.noticeboard.dto.UserDto;
import com.example.noticeboard.entity.UserEntity;
import com.example.noticeboard.mapper.UserEntityModelMapper;
import com.example.noticeboard.model.UserModel;
import com.example.noticeboard.repository.UserRepository;
import com.example.noticeboard.util.PasswordEncoderUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)  // MockitoExtension을 사용하여 @Mock 객체를 초기화
public class UserServiceTest {

    @InjectMocks
    private UserService userService;  // 테스트 대상 서비스 클래스

    @Mock
    private UserRepository userRepository;  // UserRepository Mock 객체

    @Mock
    private PasswordEncoderUtil passwordEncoderUtil;  // PasswordEncoderUtil Mock 객체

    @Mock
    private UserEntityModelMapper userEntityModelMapper;

    // 로그인 요청 처리 테스트
    @Test
    public void testAuthenticateUser_Success() {
        // Given
        String username = "user3";
        String password = "password";

        // Mock UserRepository
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword("$2a$10$.tEoJVBQsWYQFTPOCddnyuhR40k/TvScNYmdHOzb13jK906OkDge2");  // 암호화된 비밀번호

        // userRepository Mocking: findByUsername()이 호출되면 userEntity를 반환하도록 설정
        when(userRepository.findByUsername(username)).thenReturn(userEntity);

        // Mock PasswordEncoderUtil: matches()가 호출되면 true를 반환하도록 설정
        when(passwordEncoderUtil.matches(password, userEntity.getPassword())).thenReturn(true);

        // When
        boolean result = userService.authenticateUser(username, password);

        // Then
        assertTrue(result);  // 인증 성공
    }

    @Test
    public void testAuthenticateUser_Failure_UserNotFound() {
        // Given
        String username = "nonexistentuser";
        String password = "password";

        // Mock UserRepository: findByUsername()이 호출되면 null을 반환하도록 설정
        when(userRepository.findByUsername(username)).thenReturn(null);

        // When
        boolean result = userService.authenticateUser(username, password);

        // Then
        assertFalse(result);  // 인증 실패 (사용자 없음)
    }

    @Test
    public void testAuthenticateUser_Failure_PasswordMismatch() {
        // Given
        String username = "user3";
        String password = "wrongpassword";

        // Mock UserRepository
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword("$2a$10$.tEoJVBQsWYQFTPOCddnyuhR40k/TvScNYmdHOzb13jK906OkDge2\t");

        // Mock UserRepository: findByUsername()이 호출되면 userEntity를 반환하도록 설정
        when(userRepository.findByUsername(username)).thenReturn(userEntity);

        // Mock PasswordEncoderUtil: matches()가 호출되면 false를 반환하도록 설정
        when(passwordEncoderUtil.matches(password, userEntity.getPassword())).thenReturn(false);

        // When
        boolean result = userService.authenticateUser(username, password);

        // Then
        assertFalse(result);  // 인증 실패 (비밀번호 불일치)
    }

    // 회원가입 테스트
    @Test
    public void testRegisterUser_Success() {
        // Given
        UserDto userDto = new UserDto();
        userDto.setUsername("user4");
        userDto.setPassword("password");

        String encodedPassword = "$2a$10$encodedPassword"; // Mock된 암호화된 비밀번호

        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(1L);
        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(encodedPassword);
        userEntity.setAuthorities(Set.of("ROLE_USER"));

        UserModel userModel = new UserModel();
        userModel.setModelId(userEntity.getUserId());
        userModel.setModelUsername(userEntity.getUsername());

        // Mocking
        when(passwordEncoderUtil.encode(userDto.getPassword())).thenReturn(encodedPassword);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        when(userEntityModelMapper.toModel(userEntity)).thenReturn(userModel);

        // When
        UserModel result = userService.registerUser(userDto);

        // Then
        assertNotNull(result);
        assertEquals(userModel.getModelId(), result.getModelId());
        assertEquals(userModel.getModelUsername(), result.getModelUsername());

        // Verify interactions
        verify(passwordEncoderUtil).encode(userDto.getPassword());
        verify(userRepository).save(any(UserEntity.class));
        verify(userEntityModelMapper).toModel(userEntity);
    }

    // 사용자 조회
    @Test
    public void testGetUserById_Success() {
        // Given
        Long userId = 1L;

        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userId);
        userEntity.setUsername("user");

        UserModel userModel = new UserModel();
        userModel.setModelId(userId);
        userModel.setModelUsername("user");

        // Mocking
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(userEntityModelMapper.toModel(userEntity)).thenReturn(userModel);

        // When
        UserModel result = userService.getUserById(userId);

        // Then
        assertNotNull(result);
        assertEquals(userModel.getModelId(), result.getModelId());
        assertEquals(userModel.getModelUsername(), result.getModelUsername());

        // Verify interactions
        verify(userRepository).findById(userId);
        verify(userEntityModelMapper).toModel(userEntity);
    }

    @Test
    public void testGetUserById_UserNotFound() {
        // Given
        Long userId = 1L;

        // Mocking
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.getUserById(userId));
        assertEquals("User not found", exception.getMessage());

        // Verify interactions
        verify(userRepository).findById(userId);
        verifyNoInteractions(userEntityModelMapper);
    }
}
