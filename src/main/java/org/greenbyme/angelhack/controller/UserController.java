package org.greenbyme.angelhack.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.greenbyme.angelhack.domain.user.User;
import org.greenbyme.angelhack.service.FileUploadDownloadService;
import org.greenbyme.angelhack.service.UserService;
import org.greenbyme.angelhack.service.dto.personalmission.PersonalMissionByUserDto;
import org.greenbyme.angelhack.service.dto.post.PostDetailResponseDto;
import org.greenbyme.angelhack.service.dto.user.*;
import org.greenbyme.angelhack.util.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
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
@Api(tags = "2. User")
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    private FileUploadDownloadService service;

    @PostMapping
    public ResponseEntity<UserResponseDto> saveUser(@RequestBody final UserSaveRequestDto requestDto) {
        UserResponseDto responseDto = userService.saveUser(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @ApiOperation(value = "이메일, 패스워드를 받아서 로그인하여 토큰을 반환한다")
    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody final UserLoginRequestDto userLoginRequestDto) {
        User user = userService.login(userLoginRequestDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(jwtTokenProvider.createToken(user.getId(), user.getRoles()));
    }
  
    @GetMapping("/images/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request){
        Resource resource = service.loadFileAsResource(fileName);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping
    public ResponseEntity<UserDetailResponseDto> getUserDetail(@ApiIgnore final Authentication authentication) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserDetail(userId));
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("/expectTreeCo2")
    public ResponseEntity<UserExpectTreeCo2ResponseDto> getUserExpectTreeCo2(@ApiIgnore final Authentication authentication) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        UserExpectTreeCo2ResponseDto userExpectTreeCo2 = userService.getUserExpectTreeCo2(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userExpectTreeCo2);
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("/personalMissions")
    public ResponseEntity<List<PersonalMissionByUserDto>> getUserPersonalMissionList(@ApiIgnore final Authentication authentication) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        List<PersonalMissionByUserDto> dto = userService.getPersonalMissionList(userId);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("/posts")
    public ResponseEntity<List<PostDetailResponseDto>> getUserPostList(@ApiIgnore final Authentication authentication) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        List<PostDetailResponseDto> dto = userService.getPostList(userId);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PutMapping("/nickname")
    public ResponseEntity<UserResponseDto> updateUserNickName(@ApiIgnore final Authentication authentication,
                                                              final UserUpdateNicktDto dto) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.updateNickName(dto, userId));
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PutMapping("/image")
    public ResponseEntity<UserResponseDto> updateUserPhotos(@ApiIgnore final Authentication authentication,
                                                            @RequestParam("file") MultipartFile file) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.updatePhotos(userId, file));
    }
}
