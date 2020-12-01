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
public class MissionFindAllResponseDto {

    private Long id;
    private Category category;
    private DayCategory dayCategory;
    private String title;
    private String subject;
    private String description;
    private String pictureUrl;
    private Long passCandidates;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public MissionFindAllResponseDto(Mission mission) {
        this.id = mission.getId();
        this.category = mission.getCategory();
        this.dayCategory = mission.getDayCategory();
        this.title = mission.getTitle();
        this.subject = mission.getSubject();
        this.description = mission.getDescription();
        this.pictureUrl = mission.getPictureUrl();
        this.passCandidates = mission.getPassCandidatesCount();
        this.startDate = LocalDateTime.now();
        this.endDate = LocalDateTime.now().plusDays(mission.getDayCategory().getDay());
    }
}
