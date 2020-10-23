package org.greenbyme.angelhack.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.greenbyme.angelhack.domain.user.User;
import org.greenbyme.angelhack.exception.ErrorResponse;
import org.greenbyme.angelhack.service.FileUploadDownloadService;
import org.greenbyme.angelhack.service.UserService;
import org.greenbyme.angelhack.service.dto.BasicResponseDto;
import org.greenbyme.angelhack.service.dto.page.PageDto;
import org.greenbyme.angelhack.service.dto.personalmission.PersonalMissionByUserDto;
import org.greenbyme.angelhack.service.dto.post.PostDetailResponseDto;
import org.greenbyme.angelhack.service.dto.user.*;
import org.greenbyme.angelhack.util.FileDownloadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;

@Api(tags = "2. User")
@Slf4j
@Validated
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Autowired
    private FileUploadDownloadService service;

    @ApiOperation(value = "유저 가입")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "가입 성공", response = BasicResponseDto.class),
            @ApiResponse(code = 409, message = "1.중복된 이메일 \t\n 2.중복된 닉네임", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "서버 에러", response = ErrorResponse.class)
    })
    @PostMapping("/signup")
    public ResponseEntity<BasicResponseDto<String>> saveUser(@Valid @RequestBody final UserSaveRequestDto requestDto) {
        String result = userService.saveUser(requestDto);
        log.info("유저 가입 완료");
        return ResponseEntity.status(HttpStatus.CREATED).body(BasicResponseDto.of(result, HttpStatus.CREATED.value()));
    }

    @ApiOperation(value = "소셜 연동 가입")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "소셜 가입 성공", response = BasicResponseDto.class),
            @ApiResponse(code = 409, message = "1.중복된 이메일 \t\n 2.중복된 닉네임", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "서버 에러", response = ErrorResponse.class)
    })
    @PostMapping("/signup/social")
    public ResponseEntity<BasicResponseDto<String>> saveSocialUser(@Valid @RequestBody final SocialUserSaveRequestDto requestDto) {
        String token = userService.saveSocialUser(requestDto);
        log.info("소셜 유저 로그인 완료");
        return ResponseEntity.status(HttpStatus.CREATED).body(BasicResponseDto.of(token, HttpStatus.CREATED.value()));
    }

    @ApiOperation(value = "이메일, 패스워드를 받아서 로그인하여 토큰을 반환한다")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "로그인 성공", response = BasicResponseDto.class),
            @ApiResponse(code = 400, message = "1.등록되지 않은 이메일 \t\n 2.틀린 암호", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "서버 에러", response = ErrorResponse.class)
    })
    @PostMapping("/signin")
    public ResponseEntity<BasicResponseDto<String>> signIn(@Valid @RequestBody final UserLoginRequestDto userLoginRequestDto) {
        String token = userService.login(userLoginRequestDto);
        log.info("유저 로그인 완료");
        return ResponseEntity.status(HttpStatus.OK).body(BasicResponseDto.of(token, (HttpStatus.OK.value())));
    }

    @ApiOperation(value = "소셜 유저 로그인")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "로그인 성공", response = BasicResponseDto.class),
            @ApiResponse(code = 400, message = "등록되지 않은 소셜 유저", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "서버 에러", response = ErrorResponse.class)
    })
    @PostMapping("/signin/social")
    public ResponseEntity<BasicResponseDto<String>> socialSignIn(@Valid @RequestBody final SocialUserLoginRequestDto socialUserLoginRequestDto) {
        String token = userService.socialLogin(socialUserLoginRequestDto);
        log.info("소셜 로그인 완료");
        return ResponseEntity.status(HttpStatus.OK).body(BasicResponseDto.of(token, HttpStatus.OK.value()));
    }

    @ApiOperation(value = "이미지 불러 오기")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "이미지 조회 성공", response = Resource.class),
            @ApiResponse(code = 400, message = "파일 조회 실패", response = FileDownloadException.class)
    })
    @GetMapping("/images/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = service.loadFileAsResource(fileName);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("Could not determine file type.");
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @ApiOperation(value = "유저 정보 상세 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "조회 성공", response = UserDetailResponseDto.class),
            @ApiResponse(code = 400, message = "등록되지 않은 유저", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "권한 없음", response = ErrorResponse.class)
    })
    @GetMapping
    public ResponseEntity<BasicResponseDto<UserDetailResponseDto>> getUserDetail(@ApiIgnore final Authentication authentication) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        UserDetailResponseDto dto = userService.getUserDetail(userId);
        log.info("유저 정보 상세 조회 완료");
        return ResponseEntity.status(HttpStatus.OK).body(BasicResponseDto.of(dto, HttpStatus.OK.value()));
    }

    @ApiOperation(value = "유저 감소량 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "조회 성공", response = UserExpectTreeCo2ResponseDto.class),
            @ApiResponse(code = 400, message = "등록되지 않은 유저", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "권한 없음", response = ErrorResponse.class)
    })
    @GetMapping("/expectTreeCo2")
    public ResponseEntity<BasicResponseDto<UserExpectTreeCo2ResponseDto>> getUserExpectTreeCo2(@ApiIgnore final Authentication authentication) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        UserExpectTreeCo2ResponseDto userExpectTreeCo2 = userService.getUserExpectTreeCo2(userId);
        log.info("유저 감소량 조회 완료");
        return ResponseEntity.status(HttpStatus.OK).body(BasicResponseDto.of(userExpectTreeCo2, HttpStatus.OK.value()));
    }

    @ApiOperation(value = "유저 전체 개인 미션 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "조회 성공", response = PersonalMissionByUserDto.class),
            @ApiResponse(code = 400, message = "등록되지 않은 유저", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "권한 없음", response = ErrorResponse.class)
    })
    @GetMapping("/personalMissions")
    public ResponseEntity<BasicResponseDto<PageDto<PersonalMissionByUserDto>>> getUserPersonalMissionList(@ApiIgnore final Authentication authentication,
                                                                                                          @PageableDefault(size = 10) final Pageable pageable) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        Page<PersonalMissionByUserDto> dto = userService.getPersonalMissionList(userId, pageable);
        log.info("유저 진행 미션 조회 완료");
        return ResponseEntity.status(HttpStatus.OK).body(BasicResponseDto.of(new PageDto<>(dto), HttpStatus.OK.value()));
    }

    @ApiOperation(value = "유저 게시글 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "조회 성공", response = PostDetailResponseDto.class),
            @ApiResponse(code = 400, message = "등록되지 않은 유저", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "권한 없음", response = ErrorResponse.class)
    })
    @GetMapping("/posts")
    public ResponseEntity<BasicResponseDto<PageDto<PostDetailResponseDto>>> getUserPostList(@ApiIgnore final Authentication authentication,
                                                                                            @PageableDefault final Pageable pageable) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        Page<PostDetailResponseDto> dto = userService.getPostList(userId, pageable);
        log.info("유저 게시글 조회 완료");
        return ResponseEntity.status(HttpStatus.OK).body(BasicResponseDto.of(new PageDto<>(dto), HttpStatus.OK.value()));
    }

    @ApiOperation(value = "유저 프로필 수정")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "프로필 수정 성공", response = UserResponseDto.class),
            @ApiResponse(code = 400, message = "1.등록되지 않은 유저 \t\n 2.반드시 값이 있어야 합니다.", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "권한 없음", response = ErrorResponse.class)
    })
    @PutMapping
    public ResponseEntity<BasicResponseDto<UserResponseDto>> updateUserProfile(@ApiIgnore final Authentication authentication,
                                                                              @Valid final UserUpdateNicktDto dto,
                                                                              @RequestParam(value = "file", required = false) final MultipartFile file) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        log.info("유저 프로필 수정 완료");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(BasicResponseDto.of(userService.updateProfile(userId, file, dto), HttpStatus.ACCEPTED.value()));
    }

    @ApiOperation(value = "토큰 Refresh")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "토큰 재발급 성공", response = BasicResponseDto.class),
            @ApiResponse(code = 401, message = "권한 없음", response = ErrorResponse.class)
    })
    @PostMapping("/refresh")
    public ResponseEntity<BasicResponseDto<String>> refreshToken(@ApiIgnore final Authentication authentication) throws Exception {
        log.info("토큰 Refresh 완료");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(BasicResponseDto.of(userService.refreshToken(authentication), HttpStatus.CREATED.value()));
    }

    @ApiOperation(value = "이메일 중복 체크")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "체크 성공", response = BasicResponseDto.class),
            @ApiResponse(code = 500, message = "서버 에러", response = ErrorResponse.class)
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<BasicResponseDto<Boolean>> checkEmail(@PathVariable("email") @Email String email) {
        log.info("이메일 중복 체크 완료");
        return ResponseEntity.status(HttpStatus.OK).body(BasicResponseDto.of(userService.checkEmail(email), HttpStatus.OK.value()));
    }

    @ApiOperation(value = "닉네임 중복 체크", response = Boolean.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "체크 성공", response = BasicResponseDto.class),
            @ApiResponse(code = 500, message = "서버 에러", response = ErrorResponse.class)
    })
    @GetMapping("/nickname/{nickname}")
    public ResponseEntity<BasicResponseDto<Boolean>> checkNickName(@PathVariable("nickname") @NotEmpty String nickname) {
        log.info("닉네임 중복 체크 완료");
        return ResponseEntity.status(HttpStatus.OK).body(BasicResponseDto.of(userService.checkNickName(nickname), HttpStatus.OK.value()));
    }
}
