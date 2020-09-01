package org.greenbyme.angelhack.service.dto.mission;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.Category.Category;
import org.greenbyme.angelhack.domain.Category.DayCategory;
import org.greenbyme.angelhack.domain.mission.Mission;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MissionFindAllByCategoryAndDayCategoryResponseDto {

    private Long id;
    private Category category;
    private DayCategory dayCategory;
    private String title;
    private String subject;
    private String description;
    private LocalDateTime ifStartDate;
    private LocalDateTime ifEndDate;
    private String pictureUrl;
    private Long passCandidates;

    public MissionFindAllByCategoryAndDayCategoryResponseDto(Mission mission) {
        this.id = mission.getId();
        this.category = mission.getCategory();
        this.dayCategory = mission.getDayCategory();
        this.title = mission.getTitle();
        this.subject = mission.getSubject();
        this.description = mission.getDescription();
        this.ifStartDate = LocalDateTime.now();
        this.ifEndDate = LocalDateTime.now().plusDays(mission.getDayCategory().getDay());
        this.pictureUrl= mission.getPictureUrl();
        this.passCandidates = mission.getPassCandidatesCount();
    }
}
