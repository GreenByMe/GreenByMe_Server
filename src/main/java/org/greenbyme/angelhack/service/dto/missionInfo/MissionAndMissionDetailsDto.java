package org.greenbyme.angelhack.service.dto.missionInfo;

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
    private String subject;
    private String description;
    private double expectTree;
    private double expectCo2;
    private MissionCertificationMethod missionCertificationMethod;

    @Builder
    public MissionAndMissionDetailsDto(Mission mission) {
        id = mission.getId();
        category = mission.getCategory();
        dayCategory = mission.getDayCategory();
        subject = mission.getSubject();
        description = mission.getDescription();
        expectTree = mission.getExpectTree();
        expectCo2 = mission.getExpectCo2();
        missionCertificationMethod = mission.getMissionCertificationMethod();
    }
}
