package org.greenbyme.angelhack.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.greenbyme.angelhack.domain.user.User;
import org.greenbyme.angelhack.service.PersonalMissionService;
import org.greenbyme.angelhack.service.dto.page.PageDto;
import org.greenbyme.angelhack.service.dto.personalmission.InProgressResponseDto;
import org.greenbyme.angelhack.service.dto.personalmission.PersonalMissionDeleteResponseDto;
import org.greenbyme.angelhack.service.dto.personalmission.PersonalMissionDetailResponseDto;
import org.greenbyme.angelhack.service.dto.personalmission.PersonalMissionSaveResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(tags = "4. PersonalMission")
@Slf4j
@RestController
@RequestMapping("/api/personalmissions")
@RequiredArgsConstructor
public class PersonalMissionController {

    private final PersonalMissionService personalMissionService;

    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping("/missions/{missionId}")
    public ResponseEntity<PersonalMissionSaveResponseDto> join(@ApiIgnore final Authentication authentication,
                                                               @PathVariable("missionId") final Long missionId) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        PersonalMissionSaveResponseDto personalMissionSaveResponseDto = personalMissionService.save(userId, missionId);
        return ResponseEntity.status(HttpStatus.CREATED).body(personalMissionSaveResponseDto);
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("/{personalMissionId}")
    public ResponseEntity<PersonalMissionDetailResponseDto> PersonalMissionDetail(@ApiIgnore final Authentication authentication,
                                                                                  @PathVariable("personalMissionId") final Long personalMissionId) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        PersonalMissionDetailResponseDto personalMissionDetailResponseDto = personalMissionService.findPersonalMissionDetails(personalMissionId, userId);
        return ResponseEntity.status(HttpStatus.OK).body(personalMissionDetailResponseDto);
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @DeleteMapping("/{personalMissionId}")
    public ResponseEntity<PersonalMissionDeleteResponseDto> personalMissionDelete(@ApiIgnore final Authentication authentication,
                                                                                  @PathVariable("personalMissionId") final Long personalMissionId) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        PersonalMissionDeleteResponseDto personalMissionDeleteResponseDto = personalMissionService.personalMissionDelete(personalMissionId, userId);
        return ResponseEntity.status(HttpStatus.OK).body(personalMissionDeleteResponseDto);
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping
    public ResponseEntity<PageDto<InProgressResponseDto>> getPersonalMissionsInProgress(@ApiIgnore final Authentication authentication,
                                                                                        @PageableDefault(size = 10) Pageable pageable) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        Page<InProgressResponseDto> responseDto = personalMissionService.getPersonalMissionInProgress(userId, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(new PageDto<>(responseDto));
    }
}
