package org.greenbyme.angelhack.service.dto.mission;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.Category.Category;
import org.greenbyme.angelhack.domain.Category.DayCategory;
import org.greenbyme.angelhack.domain.mission.Mission;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MissionFindAllByCategoryResponseDto {
    private Category category;
    private DayCategory dayCategory;
    private String subject;
    private String description;

    public MissionFindAllByCategoryResponseDto(Mission mission) {
        category = mission.getCategory();
        dayCategory = mission.getDayCategory();
        subject = mission.getSubject();
        description = mission.getDescription();
    }
}
