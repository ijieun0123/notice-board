package com.example.noticeboard.service;

import com.example.noticeboard.dto.PostDto;
import com.example.noticeboard.entity.PostEntity;
import com.example.noticeboard.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @InjectMocks
    private PostService postService;  // 테스트할 서비스 클래스

    @Mock
    private PostRepository postRepository;  // Mocking PostRepository

    // 게시글 목록 가져오기
    @Test
    public void testFindAll() {
        // Given
        PostEntity postEntity1 = new PostEntity();
        postEntity1.setId(1L);
        postEntity1.setTitle("Post 1");
        postEntity1.setContent("Content 1");

        PostEntity postEntity2 = new PostEntity();
        postEntity2.setId(2L);
        postEntity2.setTitle("Post 2");
        postEntity2.setContent("Content 2");

        List<PostEntity> postEntities = Arrays.asList(postEntity1, postEntity2);

        // Mocking postRepository.findAll() to return a list of PostEntities
        when(postRepository.findAll()).thenReturn(postEntities);

        // When
        List<PostDto> result = postService.findAll();

        // Then
        assertNotNull(result);  // 결과가 null이 아님을 확인
        assertEquals(2, result.size());  // 결과 리스트의 크기 검증
        assertEquals("Post 1", result.get(0).getTitle());  // 첫 번째 포스트 제목 검증
        assertEquals("Post 2", result.get(1).getTitle());  // 두 번째 포스트 제목 검증

        // Verify that postRepository.findAll() was called once
        verify(postRepository, times(1)).findAll();
    }

    // 게시글 저장하기
    @Test
    public void testSave() {
        // Given
        PostDto postDto = new PostDto(1L, "Post 1", "Content 1");  // 테스트용 DTO 생성

        // When
        postService.save(postDto);  // 서비스 메서드 호출

        // Then
        // ArgumentCaptor를 사용하여 실제로 save()에 전달된 인수를 캡처
        ArgumentCaptor<PostEntity> captor = ArgumentCaptor.forClass(PostEntity.class);
        verify(postRepository, times(1)).save(captor.capture());  // save() 호출 시 전달된 인수를 캡처

        // 캡처된 객체와 예상한 객체의 내용을 비교
        PostEntity capturedPostEntity = captor.getValue();
        assertEquals(postDto.getId(), capturedPostEntity.getId());
        assertEquals(postDto.getTitle(), capturedPostEntity.getTitle());
        assertEquals(postDto.getContent(), capturedPostEntity.getContent());
    }

    // 게시글 찾기
    @Test
    public void testFindById_Success() {
        // Given
        Long postId = 1L;
        PostEntity postEntity = new PostEntity();
        postEntity.setId(postId);
        postEntity.setTitle("Post Title");
        postEntity.setContent("Post Content");

        // Mocking PostRepository
        when(postRepository.findById(postId)).thenReturn(Optional.of(postEntity));

        // When
        PostDto result = postService.findById(postId);

        // Then
        assertNotNull(result);
        assertEquals(postId, result.getId());
        assertEquals("Post Title", result.getTitle());
        assertEquals("Post Content", result.getContent());
    }

    @Test
    public void testFindById_PostNotFound() {
        // Given
        Long postId = 1L;

        // Mocking PostRepository to return an empty Optional
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            postService.findById(postId);
        });
        assertEquals("Post not found", exception.getMessage());
    }

    // 게시글 삭제
    @Test
    public void testDelete_Success() {
        // Given
        Long postId = 1L;

        // When
        postService.delete(postId);  // delete 메서드 호출

        // Then
        verify(postRepository, times(1)).deleteById(postId);  // deleteById가 한 번 호출되었는지 확인
    }
}


