package org.greenbyme.angelhack.domain.mission;

import org.greenbyme.angelhack.domain.category.Category;
import org.greenbyme.angelhack.domain.category.DayCategory;
import org.greenbyme.angelhack.service.dto.mission.MissionFindAllByCategoryAndDayCategoryResponseDto;
import org.greenbyme.angelhack.service.dto.page.PopularMissionHomePageResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MissionQueryDsl {

    Page<MissionFindAllByCategoryAndDayCategoryResponseDto> findAllByCategoryAndDayCategory(Category category, DayCategory dayCategory, Pageable pageable);

    List<PopularMissionHomePageResponseDto> findPopularMission();
}
