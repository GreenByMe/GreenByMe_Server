package org.greenbyme.angelhack.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.greenbyme.angelhack.service.CategoryService;
import org.greenbyme.angelhack.service.dto.category.EnumWithSingleValueResDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "6. Category")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/categorys")
public class CategoryController {

    private final CategoryService categoryService;

    @ApiOperation(value = "Category Class Type 조회")
    @GetMapping
    public ResponseEntity<EnumWithSingleValueResDto> findAllCategoryType(){
        EnumWithSingleValueResDto categoryResDto = categoryService.findAllCategoryType();
        log.info("Category values 조회 완료 ");
        return ResponseEntity.status(HttpStatus.OK).body(categoryResDto);
    }

    @ApiOperation(value = "DayCategory Class Type 조회")
    @GetMapping("/days")
    public ResponseEntity<EnumWithSingleValueResDto> findAllDayCategoryType(){
        EnumWithSingleValueResDto categoryResDto = categoryService.findAllDayCategoryType();
        log.info("DayCategory values 조회 완료 ");
        return ResponseEntity.status(HttpStatus.OK).body(categoryResDto);
    }

    @ApiOperation(value = "MissionCertificateCount Class Type 조회")
    @GetMapping("/certificate/counts")
    public ResponseEntity<EnumWithSingleValueResDto> findAllMissionCertificateCountType(){
        EnumWithSingleValueResDto categoryResDto = categoryService.findAllMissionCertificateCountType();
        log.info("MissionCertificateCount values 조회 완료 ");
        return ResponseEntity.status(HttpStatus.OK).body(categoryResDto);
    }
}
