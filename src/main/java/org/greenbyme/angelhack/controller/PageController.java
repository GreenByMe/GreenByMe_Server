package org.greenbyme.angelhack.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.greenbyme.angelhack.domain.user.User;
import org.greenbyme.angelhack.service.PageService;
import org.greenbyme.angelhack.service.dto.page.CertPageDto;
import org.greenbyme.angelhack.service.dto.page.HomePageDto;
import org.greenbyme.angelhack.service.dto.page.MyPageDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("/home")
    public ResponseEntity<HomePageDto> getHomePage(@ApiIgnore final Authentication authentication,
                                                   @PageableDefault(size = 10, sort = "passCandidatesCount", direction = Sort.Direction.DESC) Pageable pageable) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        return ResponseEntity.status(HttpStatus.OK).body(pageService.getHompeageInfos(userId, pageable));
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("/cert")
    public ResponseEntity<CertPageDto> getCertPage(@ApiIgnore final Authentication authentication) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        return ResponseEntity.status(HttpStatus.OK).body(pageService.getCertPage(userId));
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping
    public ResponseEntity<MyPageDto> getMyPage(@ApiIgnore final Authentication authentication) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        return ResponseEntity.status(HttpStatus.OK).body(pageService.getMyPage(userId));
    }
}
