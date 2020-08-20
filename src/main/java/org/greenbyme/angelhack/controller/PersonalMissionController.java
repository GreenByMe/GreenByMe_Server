package org.greenbyme.angelhack.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.greenbyme.angelhack.domain.user.User;
import org.greenbyme.angelhack.service.PersonalMissionService;
import org.greenbyme.angelhack.service.dto.personalmission.InProgressResponseDto;
import org.greenbyme.angelhack.service.dto.personalmission.PersonalMissionDeleteResponseDto;
import org.greenbyme.angelhack.service.dto.personalmission.PersonalMissionDetailResponseDto;
import org.greenbyme.angelhack.service.dto.personalmission.PersonalMissionSaveResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(tags = "4. PersonalMission")
@Slf4j
@RestController
@RequestMapping("/api/personalMissions")
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

    @GetMapping("/{personalMission_id}")
    public ResponseEntity<PersonalMissionDetailResponseDto> PersonalMissionDetail(@PathVariable("personalMission_id") final Long id) {
        PersonalMissionDetailResponseDto personalMissionDetailResponseDto = personalMissionService.findPersonalMissionDetails(id);
        return ResponseEntity.status(HttpStatus.OK).body(personalMissionDetailResponseDto);
    }

    @DeleteMapping("/{personalMission_id}")
    public ResponseEntity<PersonalMissionDeleteResponseDto> personalMissionDelete(@PathVariable("personalMission_id") final Long id) {
        PersonalMissionDeleteResponseDto personalMissionDeleteResponseDto = personalMissionService.personalMissionDelete(id);
        return ResponseEntity.status(HttpStatus.OK).body(personalMissionDeleteResponseDto);
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping
    public ResponseEntity<List<InProgressResponseDto>> getPersonalMissionsInProgress(@ApiIgnore final Authentication authentication) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        List<InProgressResponseDto> responseDto = personalMissionService.getPersonalMissionInProgress(userId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
