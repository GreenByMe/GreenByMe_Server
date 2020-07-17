package org.greenbyme.angelhack.service.dto.mission;

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
public class MissionDetailsDto {

    private Long id;
    private Category category;
    private DayCategory dayCategory;
    private String subject;
    private String description;
    private MissionCertificationMethod missionCertificationMethod;

    @Builder
    public MissionDetailsDto(Mission mission) {
        id = mission.getId();
        category = mission.getCategory();
        dayCategory = mission.getDayCategory();
        subject = mission.getSubject();
        description = mission.getDescription();
        missionCertificationMethod = mission.getMissionCertificationMethod();
    }
}
