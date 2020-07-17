package org.greenbyme.angelhack.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.greenbyme.angelhack.service.UserService;
import org.greenbyme.angelhack.service.dto.missionInfo.MissionInfobyUserDto;
import org.greenbyme.angelhack.service.dto.post.PostDetailResponseDto;
import org.greenbyme.angelhack.service.dto.user.UserDetailResponseDto;
import org.greenbyme.angelhack.service.dto.user.UserResponseDto;
import org.greenbyme.angelhack.service.dto.user.UserSaveRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> saveUser(@RequestBody final UserSaveRequestDto requestDto) {
        UserResponseDto responseDto = userService.saveUser(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDetailResponseDto> getUserDetail(@PathVariable("userId") final Long userId) {
//        UserDetailResponseDto responseDto = ;
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserDetail(userId));
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
}
