package org.greenbyme.angelhack.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.greenbyme.angelhack.domain.user.User;
import org.greenbyme.angelhack.service.MissionInfoService;
import org.greenbyme.angelhack.service.dto.missionInfo.InProgressResponseDto;
import org.greenbyme.angelhack.service.dto.missionInfo.MissionInfoDeleteResponseDto;
import org.greenbyme.angelhack.service.dto.missionInfo.MissionInfoDetailResponseDto;
import org.greenbyme.angelhack.service.dto.missionInfo.MissionInfoSaveResponseDto;
import org.greenbyme.angelhack.service.dto.page.PageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/missionInfos")
@RequiredArgsConstructor
public class MissionInfoController {

    private final MissionInfoService missionInfoService;

    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping("/missions/{missionId}")
    public ResponseEntity<MissionInfoSaveResponseDto> join(@ApiIgnore final Authentication authentication,
                                                           @PathVariable("missionId") final Long missionId) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        MissionInfoSaveResponseDto missionInfoSaveResponseDto = missionInfoService.save(userId, missionId);
        return ResponseEntity.status(HttpStatus.CREATED).body(missionInfoSaveResponseDto);
    }

    @GetMapping("/{missionInfo_id}")
    public ResponseEntity<MissionInfoDetailResponseDto> missionInfoDetail(@PathVariable("missionInfo_id") final Long id) {
        MissionInfoDetailResponseDto missionInfoDetailResponseDto = missionInfoService.findMissionInfoDetails(id);
        return ResponseEntity.status(HttpStatus.OK).body(missionInfoDetailResponseDto);
    }

    @DeleteMapping("/{missionInfo_id}")
    public ResponseEntity<MissionInfoDeleteResponseDto> missionInfoDelete(@PathVariable("missionInfo_id") final Long id) {
        MissionInfoDeleteResponseDto missionInfoDeleteResponseDto = missionInfoService.missionInfoDelete(id);
        return ResponseEntity.status(HttpStatus.OK).body(missionInfoDeleteResponseDto);
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping
    public ResponseEntity<PageDto<InProgressResponseDto>> getMissionInfosInProgress(@ApiIgnore final Authentication authentication,
                                                                                    @PageableDefault(size = 10) Pageable pageable) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        Page<InProgressResponseDto> responseDto = missionInfoService.getMissionInfoInProgress(userId, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(new PageDto<>(responseDto));
    }
}
