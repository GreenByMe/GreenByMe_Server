package org.greenbyme.angelhack.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.greenbyme.angelhack.service.UserService;
import org.greenbyme.angelhack.service.dto.TokenResponse;
import org.greenbyme.angelhack.service.dto.user.UserLoginRequestDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @PostMapping("/users")
    public TokenResponse login(@RequestBody UserLoginRequestDto dto) {
        String token = userService.createToken(dto);
        Long userId = userService.login(dto);
        return new TokenResponse(token, userId);
    }
}
