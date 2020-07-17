package org.greenbyme.angelhack.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.greenbyme.angelhack.service.PostService;
import org.greenbyme.angelhack.service.dto.post.PostUpdateRequestDto;
import org.greenbyme.angelhack.service.dto.post.PostDetailResponseDto;
import org.greenbyme.angelhack.service.dto.post.PostResponseDto;
import org.greenbyme.angelhack.service.dto.post.PostSaveRequestDto;
import org.greenbyme.angelhack.service.dto.post.PostSaveResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostSaveResponseDto> savePost(@RequestBody final PostSaveRequestDto requestDto) {
        PostSaveResponseDto responseDto = postService.savePosts(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/missions/{missionId}")
    public ResponseEntity<List<PostResponseDto>> getPostsByMission(@PathVariable("missionId") final Long missionId) {
        List<PostResponseDto> responseDtos = postService.getPostsByMission(missionId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDtos);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDetailResponseDto> getPostDetail(@PathVariable("postId") final Long postId) {
        PostDetailResponseDto responseDto = postService.getPostDetail(postId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<PostResponseDto>> getPostsByUser(@PathVariable("userId") final Long userId) {
        List<PostResponseDto> responseDtos = postService.getPostsByUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDtos);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable("postId") final Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostSaveResponseDto> updatePost(@PathVariable("postId") final Long postId, @RequestBody PostUpdateRequestDto requestDto) {
        PostSaveResponseDto responseDto = postService.updatePost(postId, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PutMapping("/{postId}/thumbsup")
    public ResponseEntity<Void> thumbsup(@PathVariable("postId") final Long postId) {
        postService.thumbsUp(postId);
        return ResponseEntity.ok().build();
    }
}
