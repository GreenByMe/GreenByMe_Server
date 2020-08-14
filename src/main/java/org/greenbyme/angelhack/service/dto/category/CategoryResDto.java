package org.greenbyme.angelhack.service.dto.category;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.Category.Category;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryResDto {

    List<Category> categories;

    public CategoryResDto(List<Category> categories){
        this.categories = categories;
    }

}
