package org.greenbyme.angelhack.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.greenbyme.angelhack.exception.ErrorResponse;
import org.greenbyme.angelhack.service.UserService;
import org.greenbyme.angelhack.service.dto.BasicResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/mail")
@RequiredArgsConstructor
public class MailController {

    private final UserService userService;

    @ApiOperation(value = "유저 가입 메일 인증")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "인증 성공", response = BasicResponseDto.class),
            @ApiResponse(code = 404, message = "만료된 인증 메일", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "서버 에러", response = ErrorResponse.class)
    })
    @GetMapping("/certificate/{token}")
    public ResponseEntity<BasicResponseDto<String>> certificateUser(@PathVariable("token") final String token) {
        String result = userService.certifiacte(token);
        return ResponseEntity.status(HttpStatus.CREATED).body(BasicResponseDto.of(result, HttpStatus.CREATED.value()));
    }
}
