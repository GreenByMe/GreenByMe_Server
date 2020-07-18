package org.greenbyme.angelhack.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.greenbyme.angelhack.domain.Category.Category;
import org.greenbyme.angelhack.domain.Category.DayCategory;
import org.greenbyme.angelhack.domain.mission.MissionCertificateCount;
import org.greenbyme.angelhack.service.MissionService;
import org.greenbyme.angelhack.service.dto.mission.*;
import org.greenbyme.angelhack.util.S3Uploader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/missions")
@RequiredArgsConstructor
public class MissionController {

    private final MissionService missionService;
    private final S3Uploader s3Uploader;

    @PostMapping("/categorys/{category}/dayCategory/{dayCategory}/missionCertificateCount/{missionCertificateCount}")
    public ResponseEntity<MissionSaveResponseDto> save(@PathVariable("category") final Category category,
                                                       @PathVariable("dayCategory") final DayCategory dayCategory,
                                                       @PathVariable("missionCertificateCount") final MissionCertificateCount missionCertificateCount,
                                                       @RequestBody MissionSaveRequestDto missionSaveRequestDto) {
        MissionSaveResponseDto missionSaveResponseDto = missionService.save(missionSaveRequestDto, category, dayCategory, missionCertificateCount);
        return ResponseEntity.status(HttpStatus.CREATED).body(missionSaveResponseDto);
    }

    @PostMapping("/upload/images")
    @ResponseBody
    public String upload(@RequestParam("data") MultipartFile multipartFile) throws IOException {
        return s3Uploader.upload(multipartFile, "static/mission");
    }

    @GetMapping("/{mission_id}")
    public ResponseEntity<MissionDetailsDto> findOneDetail(@PathVariable("mission_id") final Long id) {
        MissionDetailsDto missionDetailsDto = missionService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(missionDetailsDto);
    }

    @GetMapping("")
    public ResponseEntity<Page<MissionFindAllResponseDto>> findAllMission(@PageableDefault(size = 5, sort = {"category", "id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<MissionFindAllResponseDto> allMission = missionService.findAllMission(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(allMission);
    }

    @GetMapping("/categorys/{category}")
    public ResponseEntity<Page<MissionFindAllByCategoryResponseDto>> findAllByCategory(@PathVariable("category") final Category category,
                                                                                       @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<MissionFindAllByCategoryResponseDto> allByCategory = missionService.findAllByCategory(category, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(allByCategory);
    }

    @GetMapping("/categorys/{category}/daycategory/{datCategory}")
    public ResponseEntity<Page<MissionFindAllByCategoryAndDayCategoryResponseDto>> findAllByCategoryAndDayCategory(@PathVariable("category") final Category category,
                                                                                                                   @PathVariable("datCategory") final DayCategory dayCategory,
                                                                                                                   @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<MissionFindAllByCategoryAndDayCategoryResponseDto> allByCategoryAndDayCategory = missionService.findAllByCategoryAndDayCategory(category, dayCategory, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(allByCategoryAndDayCategory);
    }

    @DeleteMapping("/{mission_id}")
    public ResponseEntity<MissionDeleteDto> missionDelete(@PathVariable("mission_id") final Long id) {
        MissionDeleteDto missionDeleteDto = missionService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body((missionDeleteDto));
    }

    @GetMapping("/populars")
    public ResponseEntity<Page<MissionPopularResponseDto>> getPopularMission(@PageableDefault(size = 10, sort = "passCandidatesCount", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<MissionPopularResponseDto> allByPopular = missionService.findAllByPopular(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(allByPopular);
    }
}
