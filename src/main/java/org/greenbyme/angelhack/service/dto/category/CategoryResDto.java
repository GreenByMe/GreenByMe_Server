package org.greenbyme.angelhack.service.dto.category;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.Category.Category;

import java.util.HashMap;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryResDto {

    private HashMap<Category, Integer> categories;

    public CategoryResDto(HashMap<Category, Integer> categories){
        this.categories = categories;
    }
}
