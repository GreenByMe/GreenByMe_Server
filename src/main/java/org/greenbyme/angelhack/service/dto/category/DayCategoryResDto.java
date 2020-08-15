package org.greenbyme.angelhack.service.dto.category;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.Category.DayCategory;

import java.util.HashMap;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DayCategoryResDto {

    private HashMap<DayCategory, Integer> dayCategories;

    public DayCategoryResDto(HashMap<DayCategory, Integer> dayCategories) {
        this.dayCategories = dayCategories;
    }
}
