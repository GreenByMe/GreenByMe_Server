package org.greenbyme.angelhack.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.greenbyme.angelhack.domain.user.User;
import org.greenbyme.angelhack.exception.ErrorResponse;
import org.greenbyme.angelhack.service.PersonalMissionService;
import org.greenbyme.angelhack.service.dto.BasicResponseDto;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Api(tags = "4. PersonalMission")
@Slf4j
@Validated
@RestController
@RequestMapping("/api/personalmissions")
@RequiredArgsConstructor
public class PersonalMissionController {

    private final PersonalMissionService personalMissionService;

    @ApiOperation(value = "개인 미션 등록")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "등록 성공", response = PersonalMissionSaveResponseDto.class),
            @ApiResponse(code = 400, message = "1.등록되지 않은 유저 \t\n 2.등록되지 않은 미션", response = ErrorResponse.class),
            @ApiResponse(code = 409, message = "1.이미 진행 중인 미션 \t\n 2.동일 기간 내 미션 진행 중", response = ErrorResponse.class),
    })
    @PostMapping("/missions/{missionId}")
    public ResponseEntity<BasicResponseDto<PersonalMissionSaveResponseDto>> join(@ApiIgnore final Authentication authentication,
                                                                                 @PathVariable("missionId") @NotNull @Positive final Long missionId) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        PersonalMissionSaveResponseDto personalMissionSaveResponseDto = personalMissionService.save(userId, missionId);
        log.info("개인 미션 등록 완료");
        return ResponseEntity.status(HttpStatus.CREATED).body(BasicResponseDto.of(personalMissionSaveResponseDto, HttpStatus.CREATED.value()));
    }

    @ApiOperation(value = "개인 미션 상세 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "조회 성공", response = PersonalMissionDetailResponseDto.class),
            @ApiResponse(code = 400, message = "등록되지 않은 개인 미션", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "미션에 대한 권한이 없음", response = ErrorResponse.class)
    })
    @GetMapping("/{personalMissionId}")
    public ResponseEntity<BasicResponseDto<PersonalMissionDetailResponseDto>> PersonalMissionDetail(@ApiIgnore final Authentication authentication,
                                                                                                    @PathVariable("personalMissionId") @NotNull @Positive final Long personalMissionId) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        PersonalMissionDetailResponseDto personalMissionDetailResponseDto = personalMissionService.findPersonalMissionDetails(personalMissionId, userId);
        log.info("개인 상세 조회 완료");
        return ResponseEntity.status(HttpStatus.OK).body(BasicResponseDto.of(personalMissionDetailResponseDto, HttpStatus.OK.value()));
    }

    @ApiOperation(value = "개인 미션 삭제")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "삭제 성공", response = PersonalMissionDeleteResponseDto.class),
            @ApiResponse(code = 400, message = "1.등록되지 않은 개인 미션 \t\n 2.등록되지 않은 유저 정보", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "미션에 대한 권한이 없음", response = ErrorResponse.class)
    })
    @DeleteMapping("/{personalMissionId}")
    public ResponseEntity<BasicResponseDto<PersonalMissionDeleteResponseDto>> personalMissionDelete(@ApiIgnore final Authentication authentication,
                                                                                                    @PathVariable("personalMissionId") @NotNull @Positive final Long personalMissionId) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        PersonalMissionDeleteResponseDto personalMissionDeleteResponseDto = personalMissionService.personalMissionDelete(personalMissionId, userId);
        log.info("개인 미션 삭제 완료");
        return ResponseEntity.status(HttpStatus.OK).body(BasicResponseDto.of(personalMissionDeleteResponseDto, HttpStatus.OK.value()));
    }

    @ApiOperation(value = "진행 중인 개인 미션 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "조회 성공", response = InProgressResponseDto.class),
            @ApiResponse(code = 400, message = "등록되지 않은 유저 정보", response = ErrorResponse.class)
    })
    @GetMapping
    public ResponseEntity<BasicResponseDto<PageDto<InProgressResponseDto>>> getPersonalMissionsInProgress(@ApiIgnore final Authentication authentication,
                                                                                                          @PageableDefault(size = 10) Pageable pageable) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        Page<InProgressResponseDto> responseDto = personalMissionService.getPersonalMissionInProgress(userId, pageable);
        log.info("진행 중인 개인 미션 조회 완료");
        return ResponseEntity.status(HttpStatus.OK).body(BasicResponseDto.of(new PageDto<>(responseDto), HttpStatus.OK.value()));
    }
}
