package org.greenbyme.angelhack.service;

import org.greenbyme.angelhack.domain.Category.Category;
import org.greenbyme.angelhack.domain.Category.DayCategory;
import org.greenbyme.angelhack.domain.mission.MissionCertificateCount;
import org.greenbyme.angelhack.service.dto.category.EnumWithSingleValueResDto;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService {

    public EnumWithSingleValueResDto findAllCategoryType() {
        HashMap<Category, Integer> categories = new HashMap<>();
        for (Category value : Category.values()) {
            categories.put(value, value.getIdx());
        }
        return new EnumWithSingleValueResDto(sort(categories));
    }

    public EnumWithSingleValueResDto findAllDayCategoryType() {
        HashMap<DayCategory, Integer> dayCategories = new HashMap<>();
        for (DayCategory value : DayCategory.values()) {
            dayCategories.put(value, value.getDay());
        }
        return new EnumWithSingleValueResDto(sort(dayCategories));
    }

    public EnumWithSingleValueResDto findAllMissionCertificateCountType() {
        HashMap<MissionCertificateCount, Integer> certificateCounts = new HashMap<>();
        for (MissionCertificateCount value : MissionCertificateCount.values()) {
            certificateCounts.put(value, value.getCount());
        }
        return new EnumWithSingleValueResDto(sort(certificateCounts));
    }

    public TreeMap<?, Integer> sort(HashMap<?, Integer> before){
        TreeMap<Enum<?>, Integer> sorted = new TreeMap<>();
        sorted.putAll((Map<? extends Enum<?>, ? extends Integer>) before);
        return sorted;
    }
}
