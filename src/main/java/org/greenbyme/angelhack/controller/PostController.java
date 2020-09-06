package org.greenbyme.angelhack.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.greenbyme.angelhack.domain.user.User;
import org.greenbyme.angelhack.exception.ErrorResponse;
import org.greenbyme.angelhack.service.FileUploadDownloadService;
import org.greenbyme.angelhack.service.PostService;
import org.greenbyme.angelhack.service.dto.BasicResponseDto;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.IOException;

@Api(tags = "5. Post")
@Slf4j
@Validated
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @Autowired
    private FileUploadDownloadService service;

    @ApiOperation(value = "게시글 저장")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "저장 성공", response = PostSaveResponseDto.class),
            @ApiResponse(code = 400, message = "1.등록되지 않은 개인 미션 \t\n 2.등록되지 않은 유저 \t\n 3.하루 인증 횟수 초과", response = ErrorResponse.class)
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping
    public ResponseEntity<BasicResponseDto<PostSaveResponseDto>> savePost(@ApiIgnore final Authentication authentication,
                                                                          @Valid final PostSaveRequestDto requestDto,
                                                                          @RequestParam("file") final MultipartFile file) throws IOException {
        Long userId = ((User) authentication.getPrincipal()).getId();
        PostSaveResponseDto responseDto = postService.savePosts(userId, requestDto, file);
        log.info("게시글 저장 완료");
        return ResponseEntity.status(HttpStatus.CREATED).body(BasicResponseDto.of(responseDto, HttpStatus.CREATED.value()));
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
            log.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        log.info("게시글 이미지 조회 완료");

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @ApiOperation(value = "미션 관련 게시글 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "조회 성공", response = PostResponseDto.class),
            @ApiResponse(code = 400, message = "등록되지 않은 미션", response = ErrorResponse.class)
    })
    @GetMapping("/missions/{missionId}")
    public ResponseEntity<BasicResponseDto<PageDto<PostResponseDto>>> getPostsByMission(@PathVariable("missionId") @NotNull @Positive final Long missionId,
                                                                                        @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PostResponseDto> responseDtos = postService.getPostsByMission(missionId, pageable);
        log.info("미션 관련 게시글 조회 완료");
        return ResponseEntity.status(HttpStatus.OK).body(BasicResponseDto.of(new PageDto<>(responseDtos), HttpStatus.OK.value()));
    }

    @ApiOperation(value = "게시글 상세 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "조회 성공", response = PostDetailResponseDto.class),
            @ApiResponse(code = 400, message = "1.등록되지 않은 게시글 \t\n 2.등록되지 않은 유저", response = ErrorResponse.class)
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("/{postId}")
    public ResponseEntity<BasicResponseDto<PostDetailResponseDto>> getPostDetail(@ApiIgnore final Authentication authentication,
                                                                                 @PathVariable("postId") @NotNull @Positive final Long postId) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        PostDetailResponseDto responseDto = postService.getPostDetail(postId, userId);
        log.info("게시글 상세 조회 완료");
        return ResponseEntity.status(HttpStatus.OK).body(BasicResponseDto.of(responseDto, HttpStatus.OK.value()));
    }

    @ApiOperation(value = "사용자의 게시글 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "조회 성공", response = PostResponseDto.class),
            @ApiResponse(code = 400, message = "등록되지 않은 유저", response = ErrorResponse.class)
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping
    public ResponseEntity<BasicResponseDto<PageDto<PostResponseDto>>> getPostsByUser(@ApiIgnore final Authentication authentication,
                                                                                     @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        Page<PostResponseDto> responseDtos = postService.getPostsByUser(userId, pageable);
        log.info("사용자의 게시글 조회 완료");
        return ResponseEntity.status(HttpStatus.OK).body(BasicResponseDto.of(new PageDto<>(responseDtos), HttpStatus.OK.value()));
    }

    @ApiOperation(value = "게시글 삭제")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "삭제 성공", response =  Boolean.class),
            @ApiResponse(code = 400, message = "1.등록되지 않은 게시글 \t\n 2.등록되지 않은 유저", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "게시글에 대한 권한 없음", response = ErrorResponse.class)
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @DeleteMapping("/{postId}")
    public ResponseEntity<BasicResponseDto<Boolean>> deletePost(@ApiIgnore final Authentication authentication,
                                                                @PathVariable("postId") @NotNull @Positive final Long postId) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        postService.deletePost(postId, userId);
        log.info("게시글 삭제 완료");
        return ResponseEntity.ok().body(BasicResponseDto.of(Boolean.TRUE, HttpStatus.OK.value()));
    }

    @ApiOperation(value = "게시글 수정")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "수정 성공", response = PostUpdateResponseDto.class),
            @ApiResponse(code = 400, message = "1.등록되지 않은 게시글 \t\n 2.등록되지 않은 유저", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "게시글에 대한 권한 없음", response = ErrorResponse.class)
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PutMapping("/{postId}")
    public ResponseEntity<BasicResponseDto<PostUpdateResponseDto>> updatePost(@ApiIgnore final Authentication authentication,
                                                            @PathVariable("postId") @NotNull @Positive final Long postId,
                                                            @Valid @RequestBody final PostUpdateRequestDto requestDto) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        PostUpdateResponseDto responseDto = postService.updatePost(userId, postId, requestDto);
        log.info("게시글 수정 완료");
        return ResponseEntity.status(HttpStatus.OK).body(BasicResponseDto.of(responseDto, HttpStatus.OK.value()));
    }

    @ApiOperation(value = "게시글 좋아요")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "수정 성공", response = Boolean.class),
            @ApiResponse(code = 400, message = "1.등록되지 않은 게시글 \t\n 2.등록되지 않은 유저", response = ErrorResponse.class)
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PutMapping("/{postId}/thumbsup")
    public ResponseEntity<BasicResponseDto<Boolean>> thumbsup(@ApiIgnore final Authentication authentication,
                                                              @PathVariable("postId") @NotNull @Positive final Long postId) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        boolean res = postService.thumbsUp(userId, postId);
        log.info("게시글 좋아요 완료");
        return ResponseEntity.ok().body(BasicResponseDto.of(res, HttpStatus.OK.value()));
    }
}
