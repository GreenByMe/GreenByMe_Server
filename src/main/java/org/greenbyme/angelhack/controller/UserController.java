package org.greenbyme.angelhack.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.greenbyme.angelhack.service.UserService;
import org.greenbyme.angelhack.service.dto.user.UserDetailResponseDto;
import org.greenbyme.angelhack.service.dto.user.UserResponseDto;
import org.greenbyme.angelhack.service.dto.user.UserSaveRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/detail/{userId}")
    public ResponseEntity<UserDetailResponseDto> getUserDetail(@PathVariable("userId") final Long userId) {
        UserDetailResponseDto responseDto = userService.getUserDetail(userId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
