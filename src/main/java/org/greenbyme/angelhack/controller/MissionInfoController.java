package org.greenbyme.angelhack.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.greenbyme.angelhack.service.MissionInfoService;
import org.greenbyme.angelhack.service.dto.missionInfo.MissionInfoSaveResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/missionInfos")
@RequiredArgsConstructor
public class MissionInfoController {

    private final MissionInfoService missionInfoService;

    @PostMapping("/users/{userId}/missions/{missionId}")
    public ResponseEntity<MissionInfoSaveResponseDto> join(@PathVariable("userId") final Long userId,
                                                           @PathVariable("missionId") final Long missionId){
        MissionInfoSaveResponseDto missionInfoSaveResponseDto = missionInfoService.save(userId, missionId);
        return ResponseEntity.status(HttpStatus.CREATED).body(missionInfoSaveResponseDto);
    }

}
