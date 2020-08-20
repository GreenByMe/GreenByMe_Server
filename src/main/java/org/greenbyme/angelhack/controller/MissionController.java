package org.greenbyme.angelhack.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.greenbyme.angelhack.domain.Category.Category;
import org.greenbyme.angelhack.domain.Category.DayCategory;
import org.greenbyme.angelhack.service.FileUploadDownloadService;
import org.greenbyme.angelhack.service.MissionService;
import org.greenbyme.angelhack.service.dto.mission.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Api(tags = "3. Mission")
@Slf4j
@RestController
@RequestMapping("/api/missions")
@RequiredArgsConstructor
public class MissionController {

    private final MissionService missionService;
    private static final Logger logger = LoggerFactory.getLogger(MissionController.class);

    @Autowired
    private FileUploadDownloadService service;

    @PostMapping
    public ResponseEntity<MissionSaveResponseDto> save(MissionSaveRequestDto missionSaveRequestDto, @RequestParam("file") MultipartFile file) {
        MissionSaveResponseDto missionSaveResponseDto = missionService.save(missionSaveRequestDto, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(missionSaveResponseDto);
    }

    @GetMapping("/images/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request){
        // Load file as Resource
        Resource resource = service.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/{mission_id}")
    public ResponseEntity<MissionDetailsDto> findOneDetail(@PathVariable("mission_id") final Long id) {
        MissionDetailsDto missionDetailsDto = missionService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(missionDetailsDto);
    }

    @GetMapping("")
    public ResponseEntity<Page<MissionFindAllResponseDto>> findAllMission(@PageableDefault(size = 10, sort = {"category", "id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<MissionFindAllResponseDto> allMission = missionService.findAllMission(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(allMission);
    }

    @GetMapping("/categorys/{category}")
    public ResponseEntity<Page<MissionFindAllByCategoryResponseDto>> findAllByCategory(@PathVariable("category") final Category category,
                                                                                       @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<MissionFindAllByCategoryResponseDto> allByCategory = missionService.findAllByCategory(category, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(allByCategory);
    }

    @GetMapping("/categorys/{category}/daycategory/{datCategory}")
    public ResponseEntity<Page<MissionFindAllByCategoryAndDayCategoryResponseDto>> findAllByCategoryAndDayCategory(@PathVariable("category") final Category category,
                                                                                                                   @PathVariable("datCategory") final DayCategory dayCategory,
                                                                                                                   @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
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
