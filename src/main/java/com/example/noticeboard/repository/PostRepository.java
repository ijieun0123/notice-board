package com.example.noticeboard.repository;

import com.example.noticeboard.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// PostRepository는 JPA를 사용하여 데이터베이스와 상호작용합니다.

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

    Page<PostEntity> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
