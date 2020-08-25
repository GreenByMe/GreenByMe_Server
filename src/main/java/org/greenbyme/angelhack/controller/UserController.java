package org.greenbyme.angelhack.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.greenbyme.angelhack.domain.user.User;
import org.greenbyme.angelhack.exception.UserException;
import org.greenbyme.angelhack.service.FileUploadDownloadService;
import org.greenbyme.angelhack.service.UserService;
import org.greenbyme.angelhack.service.dto.BasicResponseDto;
import org.greenbyme.angelhack.service.dto.TokenResponse;
import org.greenbyme.angelhack.service.dto.page.PageDto;
import org.greenbyme.angelhack.service.dto.personalmission.PersonalMissionByUserDto;
import org.greenbyme.angelhack.service.dto.post.PostDetailResponseDto;
import org.greenbyme.angelhack.service.dto.user.*;
import org.greenbyme.angelhack.util.FileDownloadException;
import org.greenbyme.angelhack.util.JwtTokenProvider;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Api(tags = "2. User")
@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    private FileUploadDownloadService service;

    @ApiOperation(value = "유저 가입")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "가입 성공", response = BasicResponseDto.class),
            @ApiResponse(code = 500, message = "이미 가입된 메일", response = UserException.class)
    })
    @PostMapping
    public ResponseEntity<UserResponseDto> saveUser(@RequestBody final UserSaveRequestDto requestDto) {
        UserResponseDto responseDto = userService.saveUser(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @ApiOperation(value = "이메일, 패스워드를 받아서 로그인하여 토큰을 반환한다")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "로그인 성공", response = TokenResponse.class),
            @ApiResponse(code = 500, message = "로그인 실패", response = UserException.class)
    })
    @PostMapping("/signin")
    public ResponseEntity<BasicResponseDto<String>> signIn(@RequestBody final UserLoginRequestDto userLoginRequestDto) {
        String token = userService.login(userLoginRequestDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(BasicResponseDto.of(token, String.valueOf(HttpStatus.CREATED.value())));
    }

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
            logger.info("Could not determine file type.");
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
            @ApiResponse(code = 1300, message = "등록되지 않은 유저", response = UserException.class)
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping
    public ResponseEntity<UserDetailResponseDto> getUserDetail(@ApiIgnore final Authentication authentication) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserDetail(userId));
    }

    @ApiOperation(value = "유저 감소량 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "조회 성공", response = UserExpectTreeCo2ResponseDto.class),
            @ApiResponse(code = 1300, message = "등록되지 않은 유저", response = UserException.class)
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("/expectTreeCo2")
    public ResponseEntity<UserExpectTreeCo2ResponseDto> getUserExpectTreeCo2(@ApiIgnore final Authentication authentication) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        UserExpectTreeCo2ResponseDto userExpectTreeCo2 = userService.getUserExpectTreeCo2(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userExpectTreeCo2);
    }

    @ApiOperation(value = "유저 진행 미션 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "조회 성공", response = PersonalMissionByUserDto.class),
            @ApiResponse(code = 1300, message = "등록되지 않은 유저", response = UserException.class)
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("/personalMissions")
    public ResponseEntity<PageDto<PersonalMissionByUserDto>> getUserPersonalMissionList(@ApiIgnore final Authentication authentication,
                                                                                        @PageableDefault(size = 10) final Pageable pageable) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        Page<PersonalMissionByUserDto> dto = userService.getPersonalMissionList(userId, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(new PageDto<>(dto));
    }

    @ApiOperation(value = "유저 게시글 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "조회 성공", response = PostDetailResponseDto.class),
            @ApiResponse(code = 1300, message = "등록되지 않은 유저", response = UserException.class)
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("/posts")
    public ResponseEntity<PageDto<PostDetailResponseDto>> getUserPostList(@ApiIgnore final Authentication authentication,
                                                                          @PageableDefault final Pageable pageable) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        Page<PostDetailResponseDto> dto = userService.getPostList(userId, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(new PageDto<>(dto));
    }

    @ApiOperation(value = "유저 닉네임 수정")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "닉네임 수정 성공", response = UserResponseDto.class),
            @ApiResponse(code = 1300, message = "등록되지 않은 유저", response = UserException.class)
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PutMapping("/nickname")
    public ResponseEntity<UserResponseDto> updateUserNickName(@ApiIgnore final Authentication authentication,
                                                              final UserUpdateNicktDto dto) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.updateNickName(dto, userId));
    }

    @ApiOperation(value = "유저 이미지 수정")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "이미지 수정 성공", response = UserResponseDto.class),
            @ApiResponse(code = 1300, message = "등록되지 않은 유저", response = UserException.class)
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PutMapping("/image")
    public ResponseEntity<UserResponseDto> updateUserPhotos(@ApiIgnore final Authentication authentication,
                                                            @RequestParam("file") MultipartFile file) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.updatePhotos(userId, file));
    }

    @ApiOperation(value = "토큰 Refresh")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping("/refresh")
    public ResponseEntity<BasicResponseDto<String>> refreshToken(@ApiIgnore final Authentication authentication) throws Exception {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(BasicResponseDto.of(userService.refreshToken(authentication), String.valueOf(HttpStatus.CREATED.value())));
    }

    @ApiOperation(value = "이메일 중복 체크", response = Boolean.class)
    @GetMapping("/email/{email}")
    public ResponseEntity<Boolean> checkEmail(@PathVariable("email") String email) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.checkEmail(email));
    }

    @ApiOperation(value = "닉네임 중복 체크", response = Boolean.class)
    @GetMapping("/nickname/{nickname}")
    public ResponseEntity<Boolean> checkNickName(@PathVariable("nickname") String nickname) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.checkNickName(nickname));
    }
}
