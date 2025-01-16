package com.example.noticeboard.repository;

import com.example.noticeboard.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByUsername() {
        // Given
        UserEntity user = new UserEntity();
        user.setUsername("user3");
        user.setPassword("password");
        userRepository.save(user);  // 데이터베이스에 저장

        // When
        UserEntity foundUser = userRepository.findByUsername("user3");

        // Then
        assertNotNull(foundUser);
        assertEquals("user3", foundUser.getUsername());
    }

    @Test
    public void testFindByUsername_NotFound() {
        // When
        UserEntity foundUser = userRepository.findByUsername("nonexistentuser");

        // Then
        assertNull(foundUser);  // 해당 유저가 없으므로 null 반환
    }
}
