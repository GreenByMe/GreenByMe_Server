package org.greenbyme.angelhack.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.greenbyme.angelhack.service.PostService;
import org.greenbyme.angelhack.service.dto.PostResponseDto;
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

    @GetMapping("/{missionId}")
    public ResponseEntity<List<PostResponseDto>> getPostsByMission(@PathVariable final Long missionId) {
        List<PostResponseDto> responseDtos = postService.getPostsByMission(missionId);
    }
}
