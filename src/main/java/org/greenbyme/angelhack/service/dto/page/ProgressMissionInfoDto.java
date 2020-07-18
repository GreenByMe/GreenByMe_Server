package org.greenbyme.angelhack.service.dto.page;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.Category.Category;
import org.greenbyme.angelhack.domain.missionInfo.MissionInfo;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProgressMissionInfoDto {

    private Long missionInfoId;
    private String subject;
    @Enumerated(EnumType.STRING)
    private Category category;
    private String certifiaciontTest;
    private String description;
    private int progressRates;
    private int current;
    private int max;

    public ProgressMissionInfoDto(MissionInfo missionInfo) {
        this.missionInfoId = missionInfo.getId();
        this.subject = missionInfo.getMission().getSubject();
        this.category = missionInfo.getMission().getCategory();
        this.certifiaciontTest = missionInfo.getMission().getMissionCertificationMethod().getText();
        this.description = "날짜 및 인증 기간 들어가는 곳";
        this.current = missionInfo.getProgress();
        this.max = missionInfo.getFinishCount();
        this.progressRates = (int) current / max * 100;
    }
}
