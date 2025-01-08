package com.example.noticeboard.repository;

import com.example.noticeboard.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

// PostRepository는 JPA를 사용하여 데이터베이스와 상호작용합니다.

public interface PostRepository extends JpaRepository<PostEntity, Long> {
}
