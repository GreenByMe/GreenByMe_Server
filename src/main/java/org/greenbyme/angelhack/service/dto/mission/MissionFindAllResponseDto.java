package org.greenbyme.angelhack.service.dto.mission;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.Category.Category;
import org.greenbyme.angelhack.domain.Category.DayCategory;
import org.greenbyme.angelhack.domain.mission.Mission;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MissionFindAllResponseDto {

    private Long id;
    private Category category;
    private DayCategory dayCategory;
    private String subject;
    private String description;

    public MissionFindAllResponseDto(Mission mission) {
        this.id = mission.getId();
        this.category = mission.getCategory();
        this.dayCategory = mission.getDayCategory();
        this.subject = mission.getSubject();
        this.description = mission.getDescription();
    }
}
