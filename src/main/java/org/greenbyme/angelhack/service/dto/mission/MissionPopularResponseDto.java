package org.greenbyme.angelhack.service.dto.mission;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.category.Category;
import org.greenbyme.angelhack.domain.category.DayCategory;
import org.greenbyme.angelhack.domain.mission.Mission;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MissionPopularResponseDto {

    private Long missionId;
    private Category category;
    private String missionTitle;
    private String subject;
    private String description;
    private String missionPictureUrl;
    private DayCategory dayCategory;
    private double expectTree;
    private double expectCo2;
    private Long passCandidatesCount;

    public MissionPopularResponseDto(Mission mission) {
        this.missionId = mission.getId();
        this.category = mission.getCategory();
        this.missionTitle = mission.getTitle();
        this.subject = mission.getSubject();
        this.description = mission.getDescription();
        this.missionPictureUrl = mission.getPictureUrl();
        this.dayCategory = mission.getDayCategory();
        this.expectTree = mission.getExpectTree();
        this.expectCo2 = mission.getExpectCo2();
        this.passCandidatesCount = mission.getPassCandidatesCount();
    }
}
