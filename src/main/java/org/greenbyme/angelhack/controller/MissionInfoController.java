package org.greenbyme.angelhack.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.greenbyme.angelhack.service.MissionInfoService;
import org.greenbyme.angelhack.service.dto.missionInfo.InProgressResponseDto;
import org.greenbyme.angelhack.service.dto.missionInfo.MissionInfoDeleteResponseDto;
import org.greenbyme.angelhack.service.dto.missionInfo.MissionInfoDetailResponseDto;
import org.greenbyme.angelhack.service.dto.missionInfo.MissionInfoSaveResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/missionInfos")
@RequiredArgsConstructor
public class MissionInfoController {

    private final MissionInfoService missionInfoService;

    @PostMapping("/users/{userId}/missions/{missionId}")
    public ResponseEntity<MissionInfoSaveResponseDto> join(@PathVariable("userId") final Long userId,
                                                           @PathVariable("missionId") final Long missionId) {
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

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<InProgressResponseDto>> getMissionInfosInProgress(@PathVariable("userId") final Long userId) {
        List<InProgressResponseDto> responseDto = missionInfoService.getMissionInfoInProgress(userId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
