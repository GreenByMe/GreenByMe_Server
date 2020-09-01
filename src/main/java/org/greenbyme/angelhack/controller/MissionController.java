package org.greenbyme.angelhack.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.greenbyme.angelhack.domain.Category.Category;
import org.greenbyme.angelhack.domain.Category.DayCategory;
import org.greenbyme.angelhack.exception.ErrorResponse;
import org.greenbyme.angelhack.service.FileUploadDownloadService;
import org.greenbyme.angelhack.service.MissionService;
import org.greenbyme.angelhack.service.dto.BasicResponseDto;
import org.greenbyme.angelhack.service.dto.mission.*;
import org.greenbyme.angelhack.service.dto.page.PageDto;
import org.greenbyme.angelhack.util.FileDownloadException;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.IOException;

@Api(tags = "3. Mission")
@Slf4j
@Validated
@RestController
@RequestMapping("/api/missions")
@RequiredArgsConstructor
public class MissionController {

    private static final Logger logger = LoggerFactory.getLogger(MissionController.class);

    private final MissionService missionService;

    @Autowired
    private FileUploadDownloadService service;

    @ApiOperation(value = "미션 저장")
    @ApiResponses(
            @ApiResponse(code = 201, message = "미션 저장 성공", response = MissionSaveResponseDto.class)
    )
    @PostMapping
    public ResponseEntity<BasicResponseDto<MissionSaveResponseDto>> save(@Valid MissionSaveRequestDto missionSaveRequestDto, @RequestParam("file") MultipartFile file) {
        MissionSaveResponseDto missionSaveResponseDto = missionService.save(missionSaveRequestDto, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(BasicResponseDto.of(missionSaveResponseDto, HttpStatus.CREATED.value()));
    }

    @ApiOperation(value = "이미지 불러 오기")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "이미지 조회 성공", response = Resource.class),
            @ApiResponse(code = 400, message = "파일 조회 실패", response = FileDownloadException.class)
    })
    @GetMapping("/images/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
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
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @ApiOperation(value = "미션 상세 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "미션 조회 성공", response = MissionDetailsDto.class),
            @ApiResponse(code = 400, message = "존재하지 않는 미션", response = ErrorResponse.class)
    })
    @GetMapping("/{missionId}")
    public ResponseEntity<BasicResponseDto<MissionDetailsDto>> findOneDetail(@PathVariable("missionId") @NotNull @Positive final Long id) {
        MissionDetailsDto missionDetailsDto = missionService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(BasicResponseDto.of(missionDetailsDto, HttpStatus.OK.value()));
    }

    @ApiOperation(value = "카테고리, 기간 내 미션 전체 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "조회 성공", response = MissionFindAllByCategoryAndDayCategoryResponseDto.class)
    })
    @GetMapping("/categorys/{category}/daycategory/{datCategory}")
    public ResponseEntity<BasicResponseDto<PageDto<MissionFindAllByCategoryAndDayCategoryResponseDto>>> findAllByCategoryAndDayCategory(@PathVariable("category") final Category category,
                                                                                                                                        @PathVariable("datCategory") final DayCategory dayCategory,
                                                                                                                                        @PageableDefault(size = 10) Pageable pageable) {
        Page<MissionFindAllByCategoryAndDayCategoryResponseDto> allByCategoryAndDayCategory = missionService.findAllByCategoryAndDayCategory(category, dayCategory, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(BasicResponseDto.of(new PageDto<>(allByCategoryAndDayCategory), HttpStatus.OK.value()));
    }

    @ApiOperation(value = "미션 삭제")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "삭제 성공", response = MissionDeleteDto.class),
            @ApiResponse(code = 400, message = "존재하지 않는 미션", response = ErrorResponse.class)
    })
    @DeleteMapping("/{missionId}")
    public ResponseEntity<BasicResponseDto<MissionDeleteDto>> missionDelete(@PathVariable("missionId") @NotNull @Positive final Long id) {
        MissionDeleteDto missionDeleteDto = missionService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(BasicResponseDto.of(missionDeleteDto, HttpStatus.OK.value()));
    }

    @ApiOperation(value = "인기 미션 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "조회 성공", response = MissionPopularResponseDto.class)
    })
    @GetMapping("/populars")
    public ResponseEntity<BasicResponseDto<PageDto<MissionPopularResponseDto>>> getPopularMission(@PageableDefault(size = 10, sort = "passCandidatesCount", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<MissionPopularResponseDto> allByPopular = missionService.findAllByPopular(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(BasicResponseDto.of(new PageDto<>(allByPopular), HttpStatus.OK.value()));
    }
}
