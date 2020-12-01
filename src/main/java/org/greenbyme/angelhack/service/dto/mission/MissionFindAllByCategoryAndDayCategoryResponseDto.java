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
public class MissionFindAllByCategoryAndDayCategoryResponseDto {

    private Long missionId;
    private Category category;
    private DayCategory dayCategory;
    private String title;
    private String subject;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String missionPictureUrl;
    private Long passCandidates;

    public MissionFindAllByCategoryAndDayCategoryResponseDto(Mission mission) {
        this.missionId = mission.getId();
        this.category = mission.getCategory();
        this.dayCategory = mission.getDayCategory();
        this.title = mission.getTitle();
        this.subject = mission.getSubject();
        this.description = mission.getDescription();
        this.startDate = LocalDateTime.now();
        this.endDate = LocalDateTime.now().plusDays(mission.getDayCategory().getDay());
        this.missionPictureUrl= mission.getPictureUrl();
        this.passCandidates = mission.getPassCandidatesCount();
    }
}
