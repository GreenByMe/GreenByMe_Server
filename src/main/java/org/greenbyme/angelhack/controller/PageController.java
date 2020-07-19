package org.greenbyme.angelhack.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.greenbyme.angelhack.service.*;
import org.greenbyme.angelhack.service.dto.page.CertPageDto;
import org.greenbyme.angelhack.service.dto.page.HomePageDto;
import org.greenbyme.angelhack.service.dto.page.MyPageDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/page")
@RequiredArgsConstructor
public class PageController {

    private final PageService pageService;

    @GetMapping("/home/users/{userId}")
    public ResponseEntity<HomePageDto> getHomePage(@PathVariable("userId") final Long userId, @PageableDefault(size = 10, sort = "passCandidatesCount", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(pageService.getHompeageInfos(userId, pageable));
    }

    @GetMapping("/cert/users/{userId}")
    public ResponseEntity<CertPageDto> getCertPage(@PathVariable("userId") final Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(pageService.getCertPage(userId));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<MyPageDto> getMyPage(@PathVariable("userId") final Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(pageService.getMyPage(userId));
    }
}
