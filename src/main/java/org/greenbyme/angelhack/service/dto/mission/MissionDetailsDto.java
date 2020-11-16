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
    private String title;
    private String subject;
    private String description;
    private double expectTree;
    private double expectCo2;
    private double cumulativeCo2;
    private double cumulativeTree;
    private String pictureUrl;
    private MissionCertificationMethod missionCertificationMethod;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long passCandidatesCount;
    private Long progressByMissionId;

    @Builder
    public MissionDetailsDto(Mission mission, Long progressByMissionId) {
        this.id = mission.getId();
        this.category = mission.getCategory();
        this.dayCategory = mission.getDayCategory();
        this.title = mission.getTitle();
        this.subject = mission.getSubject();
        this.description = mission.getDescription();
        this.expectTree = mission.getExpectTree();
        this.expectCo2 = mission.getExpectCo2();
        this.cumulativeCo2 = mission.getCumulativeCo2();
        this.cumulativeTree = mission.getCumulativeTree();
        this.missionCertificationMethod = mission.getMissionCertificationMethod();
        this.pictureUrl= mission.getPictureUrl();
        this.startDate = LocalDateTime.now();
        this.endDate = LocalDateTime.now().plusDays(mission.getDayCategory().getDay());
        this.passCandidatesCount = mission.getPassCandidatesCount();
        this.progressByMissionId = progressByMissionId;
    }
}
