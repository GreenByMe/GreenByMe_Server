package org.greenbyme.angelhack.service.dto.page;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.Category.Category;
import org.greenbyme.angelhack.domain.missionInfo.MissionInfo;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProgressMissionInfoDto {

    private Long missionInfoId;
    private String subject;
    private Category category;
    private String certifiaciontTest;
    private int progressRates;
    private int current;
    private int finishCount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public ProgressMissionInfoDto(MissionInfo missionInfo) {
        this.missionInfoId = missionInfo.getId();
        this.subject = missionInfo.getMission().getSubject();
        this.category = missionInfo.getMission().getCategory();
        this.certifiaciontTest = missionInfo.getMission().getMissionCertificationMethod().getText();
        this.startDate = missionInfo.getCreatedDate();
        this.endDate = missionInfo.getCreatedDate().plusDays(missionInfo.getMission().getDayCategory().getDay());
        this.current = missionInfo.getProgress();
        this.finishCount = missionInfo.getFinishCount();
        this.progressRates = (int) current / finishCount * 100;

    }
}
