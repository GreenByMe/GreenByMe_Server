package org.greenbyme.angelhack.controller;

;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.greenbyme.angelhack.domain.Category.Category;
import org.greenbyme.angelhack.domain.Category.DayCategory;
import org.greenbyme.angelhack.service.MissionService;
import org.greenbyme.angelhack.service.dto.mission.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/missions")
@RequiredArgsConstructor
public class MissionController {

    private final MissionService missionService;

    @PostMapping("")
    public ResponseEntity<MissionSaveResponseDto> save(@RequestBody MissionSaveRequestDto missionSaveRequestDto){
        MissionSaveResponseDto missionSaveResponseDto = missionService.save(missionSaveRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(missionSaveResponseDto);
    }

    @GetMapping("")
    public ResponseEntity<Page<MissionFindAllResponseDto>> findAllMission(@PageableDefault(size=5, sort = "category") Pageable pageable){
        Page<MissionFindAllResponseDto> allMission = missionService.findAllMission(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(allMission);
    }

    @GetMapping("/categorys/{category}")
    public ResponseEntity<Page<MissionFindAllByCategoryResponseDto>> findAllByCategory(@PathVariable("category") final Category category,
                                                                                       @PageableDefault(size=5) Pageable pageable){
        Page<MissionFindAllByCategoryResponseDto> allByCategory = missionService.findAllByCategory(category, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(allByCategory);
    }

    @GetMapping("/categorys/{category}/daycategory/{datCategory}")
    public ResponseEntity<Page<MissionFindAllByCategoryAndDayCategoryResponseDto>> findAllByCategoryAndDayCategory(@PathVariable("category") final Category category,
                                                                                                                   @PathVariable("datCategory") final DayCategory dayCategory,
                                                                                                                   @PageableDefault(size=5) Pageable pageable){
        Page<MissionFindAllByCategoryAndDayCategoryResponseDto> allByCategoryAndDayCategory = missionService.findAllByCategoryAndDayCategory(category, dayCategory, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(allByCategoryAndDayCategory);
    }

    @DeleteMapping("/{mission_id}")
    public ResponseEntity<MissionDeleteDto> missionDelete(@PathVariable("mission_id") final Long id ){
        MissionDeleteDto missionDeleteDto = missionService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body((missionDeleteDto));
    }
}
