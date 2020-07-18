package org.greenbyme.angelhack.service.dto.mission;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.Category.Category;
import org.greenbyme.angelhack.domain.Category.DayCategory;
import org.greenbyme.angelhack.domain.mission.Mission;
import org.greenbyme.angelhack.domain.mission.MissionCertificationMethod;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MissionDetailsDto {

    private Long id;
    private Category category;
    private DayCategory dayCategory;
    private String subject;
    private String description;
    private double expectTree;
    private double expectCo2;
    private MissionCertificationMethod missionCertificationMethod;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long passCandidatesCount;
    private Long progressByMissionId;

    @Builder
    public MissionDetailsDto(Mission mission, Long progressByMissionId) {
        id = mission.getId();
        category = mission.getCategory();
        dayCategory = mission.getDayCategory();
        subject = mission.getSubject();
        description = mission.getDescription();
        expectTree = mission.getExpectTree();
        expectCo2 = mission.getExpectCo2();
        missionCertificationMethod = mission.getMissionCertificationMethod();
        this.startDate = LocalDateTime.now();
        this.endDate = LocalDateTime.now().plusDays(mission.getDayCategory().getDay());
        this.passCandidatesCount = mission.getPassCandidatesCount();
        this.progressByMissionId = progressByMissionId;
    }
}
