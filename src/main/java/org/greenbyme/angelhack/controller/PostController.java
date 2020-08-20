package org.greenbyme.angelhack.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.greenbyme.angelhack.domain.user.User;
import org.greenbyme.angelhack.service.FileUploadDownloadService;
import org.greenbyme.angelhack.service.PostService;
import org.greenbyme.angelhack.service.dto.page.PageDto;
import org.greenbyme.angelhack.service.dto.post.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private FileUploadDownloadService service;

    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping
    public ResponseEntity<PostSaveResponseDto> savePost(@ApiIgnore final Authentication authentication,
                                                        final PostSaveRequestDto requestDto,
                                                        @RequestParam("file") final MultipartFile file) throws IOException {
        Long userId = ((User) authentication.getPrincipal()).getId();
        PostSaveResponseDto responseDto = postService.savePosts(userId, requestDto, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/images/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable final String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = service.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/missions/{missionId}")
    public ResponseEntity<PageDto<PostResponseDto>> getPostsByMission(@PathVariable("missionId") final Long missionId,
                                                                      @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PostResponseDto> responseDtos = postService.getPostsByMission(missionId, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(new PageDto<>(responseDtos));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDetailResponseDto> getPostDetail(@PathVariable("postId") final Long postId) {
        PostDetailResponseDto responseDto = postService.getPostDetail(postId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping
    public ResponseEntity<PageDto<PostResponseDto>> getPostsByUser(@ApiIgnore final Authentication authentication,
                                                                   @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        Page<PostResponseDto> responseDtos = postService.getPostsByUser(userId, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(new PageDto<>(responseDtos));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable("postId") final Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok().build();
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PutMapping("/{postId}")
    public ResponseEntity<PostUpdateResponseDto> updatePost(@ApiIgnore final Authentication authentication,
                                                            @PathVariable("postId") final Long postId,
                                                            @RequestBody final PostUpdateRequestDto requestDto) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        PostUpdateResponseDto responseDto = postService.updatePost(userId, postId, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PutMapping("/{postId}/thumbsup")
    public ResponseEntity<Void> thumbsup(@ApiIgnore final Authentication authentication,
                                         @PathVariable("postId") final Long postId) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        postService.thumbsUp(userId, postId);
        return ResponseEntity.ok().build();
    }
}
