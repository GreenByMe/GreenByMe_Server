package org.greenbyme.angelhack.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.greenbyme.angelhack.domain.user.User;
import org.greenbyme.angelhack.exception.ErrorResponse;
import org.greenbyme.angelhack.service.PageService;
import org.greenbyme.angelhack.service.dto.BasicResponseDto;
import org.greenbyme.angelhack.service.dto.page.CertPageDto;
import org.greenbyme.angelhack.service.dto.page.HomePageDto;
import org.greenbyme.angelhack.service.dto.page.MyPageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "1. View")
@Slf4j
@RestController
@RequestMapping("/api/page")
@RequiredArgsConstructor
public class PageController {

    private final PageService pageService;

    @ApiOperation(value = "홈 페이지 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "조회 성공", response = HomePageDto.class),
            @ApiResponse(code = 400, message = "등록되지 않은 유저", response = ErrorResponse.class)
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("/home")
    public ResponseEntity<BasicResponseDto<HomePageDto>> getHomePage(@ApiIgnore final Authentication authentication) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        return ResponseEntity.status(HttpStatus.OK).body(BasicResponseDto.of(pageService.getHomepageInfos(userId), HttpStatus.OK.value()));
    }

    @ApiOperation(value = "인증 페이지 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "조회 성공", response = CertPageDto.class),
            @ApiResponse(code = 400, message = "등록되지 않은 유저", response = ErrorResponse.class)
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("/cert")
    public ResponseEntity<BasicResponseDto<CertPageDto>> getCertPage(@ApiIgnore final Authentication authentication) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        return ResponseEntity.status(HttpStatus.OK).body(BasicResponseDto.of(pageService.getCertPage(userId), HttpStatus.OK.value()));
    }

    @ApiOperation(value = "개인 페이지 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "조회 성공", response = MyPageDto.class),
            @ApiResponse(code = 400, message = "등록되지 않은 유저", response = ErrorResponse.class)
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping
    public ResponseEntity<BasicResponseDto<MyPageDto>> getMyPage(@ApiIgnore final Authentication authentication) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        return ResponseEntity.status(HttpStatus.OK).body(BasicResponseDto.of(pageService.getMyPage(userId), HttpStatus.OK.value()));
    }
}
