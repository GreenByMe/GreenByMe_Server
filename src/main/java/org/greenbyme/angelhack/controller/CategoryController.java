package org.greenbyme.angelhack.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.greenbyme.angelhack.service.CategoryService;
import org.greenbyme.angelhack.service.dto.category.CategoryResDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/categorys")
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<CategoryResDto> findAllCategoryType(){
        CategoryResDto categoryResDto = categoryService.findAllCategoryType();
        logger.info("Category values 조회 완료 ");
        return ResponseEntity.status(HttpStatus.OK).body(categoryResDto);
    }

    //@GetMapping("/days")


}
