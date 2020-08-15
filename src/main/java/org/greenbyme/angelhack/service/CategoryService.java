package org.greenbyme.angelhack.service;

import org.greenbyme.angelhack.domain.Category.Category;
import org.greenbyme.angelhack.domain.Category.DayCategory;
import org.greenbyme.angelhack.domain.mission.MissionCertificateCount;
import org.greenbyme.angelhack.service.dto.category.CategoryResDto;
import org.greenbyme.angelhack.service.dto.category.DayCategoryResDto;
import org.greenbyme.angelhack.service.dto.category.MissionCertificateCountResDto;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService {

    public CategoryResDto findAllCategoryType(){
        List<Category> categories = new ArrayList<>(Arrays.asList(Category.values()));
        return new CategoryResDto(categories);
    }

    public DayCategoryResDto findAllDayCategoryType() {
        HashMap<DayCategory, Integer> dayCategories = new HashMap<>();
        for (DayCategory value : DayCategory.values()) {
            dayCategories.put(value,value.getDay());
        }
        return new DayCategoryResDto(dayCategories);
    }

    public MissionCertificateCountResDto findAllMissionCertificateCountType() {
        HashMap<MissionCertificateCount, Integer> certificateCounts = new HashMap<>();
        for (MissionCertificateCount value : MissionCertificateCount.values()) {
            certificateCounts.put(value, value.getCount());
        }
        return new MissionCertificateCountResDto(certificateCounts);
    }
}
