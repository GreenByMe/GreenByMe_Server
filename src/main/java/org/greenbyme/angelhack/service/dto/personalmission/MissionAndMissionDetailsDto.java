package org.greenbyme.angelhack.service.dto.personalmission;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.Category.Category;
import org.greenbyme.angelhack.domain.Category.DayCategory;
import org.greenbyme.angelhack.domain.mission.Mission;
import org.greenbyme.angelhack.domain.mission.MissionCertificationMethod;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MissionAndMissionDetailsDto {

    private Long id;
    private Category category;
    private DayCategory dayCategory;
    private String title;
    private String subject;
    private String description;
    private double expectTree;
    private double expectCo2;
    private MissionCertificationMethod missionCertificationMethod;

    @Builder
    public MissionAndMissionDetailsDto(Mission mission) {
        this.id = mission.getId();
        this.category = mission.getCategory();
        this.dayCategory = mission.getDayCategory();
        this.title = mission.getTitle();
        this.subject = mission.getSubject();
        this.description = mission.getDescription();
        this.expectTree = mission.getExpectTree();
        this.expectCo2 = mission.getExpectCo2();
        this.missionCertificationMethod = mission.getMissionCertificationMethod();
    }
}
