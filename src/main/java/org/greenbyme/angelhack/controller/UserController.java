package org.greenbyme.angelhack.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.greenbyme.angelhack.service.FileUploadDownloadService;
import org.greenbyme.angelhack.service.UserService;
import org.greenbyme.angelhack.service.dto.FileUploadResponse;
import org.greenbyme.angelhack.service.dto.missionInfo.MissionInfobyUserDto;
import org.greenbyme.angelhack.service.dto.post.PostDetailResponseDto;
import org.greenbyme.angelhack.service.dto.user.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private FileUploadDownloadService service;

    @PostMapping
    public ResponseEntity<UserResponseDto> saveUser(@RequestBody final UserSaveRequestDto requestDto) {
        UserResponseDto responseDto = userService.saveUser(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PostMapping("/images")
    public FileUploadResponse uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = service.storeFile(file);

        String filedUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/users/images/")
                .path(fileName)
                .toUriString();

        return new FileUploadResponse(fileName, filedUrl, file.getContentType(), file.getSize());
    }

    @GetMapping("/images/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request){
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
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<UserDetailResponseDto> getUserDetail(@PathVariable("userId") final Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserDetail(userId));
    }


    @GetMapping("/{userId}/expectTreeCo2")
    public ResponseEntity<UserExpectTreeCo2ResponseDto> getUserExpectTreeCo2(@PathVariable("userId") Long id){
        UserExpectTreeCo2ResponseDto userExpectTreeCo2 = userService.getUserExpectTreeCo2(id);
        return ResponseEntity.status(HttpStatus.OK).body(userExpectTreeCo2);
    }

    @GetMapping("/{userId}/missions")
    public ResponseEntity<List<MissionInfobyUserDto>> getUserMissionInfoList(@PathVariable("userId") final Long userId) {
        List<MissionInfobyUserDto> dto = userService.getMissionInfoList(userId);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping("/{userId}/posts")
    public ResponseEntity<List<PostDetailResponseDto>> getUserPostList(@PathVariable("userId") final Long userId) {
        List<PostDetailResponseDto> dto = userService.getPostList(userId);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @PutMapping("/nickname")
    public ResponseEntity<UserResponseDto> updateUserNickName(final UserUpdateNicktDto dto) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.updateNickName(dto));
    }

    @PutMapping("/photos")
    public ResponseEntity<UserResponseDto> updateUserPhotos(final UserUpdatePhotoDto dto) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.updatePhotos(dto));
    }
}
