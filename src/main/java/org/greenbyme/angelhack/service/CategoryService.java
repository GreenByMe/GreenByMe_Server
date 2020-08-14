package org.greenbyme.angelhack.service;


import org.greenbyme.angelhack.domain.Category.Category;
import org.greenbyme.angelhack.service.dto.category.CategoryResDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CategoryService {

    public CategoryResDto findAllCategoryType(){
        List<Category> categories = new ArrayList<>(Arrays.asList(Category.values()));
        return new CategoryResDto(categories);
    }
}
