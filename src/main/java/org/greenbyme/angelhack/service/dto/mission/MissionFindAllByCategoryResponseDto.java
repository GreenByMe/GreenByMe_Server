package org.greenbyme.angelhack.service.dto.mission;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.category.Category;
import org.greenbyme.angelhack.domain.category.DayCategory;
import org.greenbyme.angelhack.domain.mission.Mission;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MissionFindAllByCategoryResponseDto {

    private Long id;
    private Category category;
    private DayCategory dayCategory;
    private String title;
    private String subject;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String pictureUrl;
    private Long passCandidates;

    public MissionFindAllByCategoryResponseDto(Mission mission) {
        this.id = mission.getId();
        this.category = mission.getCategory();
        this.title = mission.getTitle();
        this.dayCategory = mission.getDayCategory();
        this.subject = mission.getSubject();
        this.description = mission.getDescription();
        this.startDate = LocalDateTime.now();
        this.endDate = LocalDateTime.now().plusDays(mission.getDayCategory().getDay());
        this.pictureUrl = mission.getPictureUrl();
        this.passCandidates = mission.getPassCandidatesCount();
    }
}
