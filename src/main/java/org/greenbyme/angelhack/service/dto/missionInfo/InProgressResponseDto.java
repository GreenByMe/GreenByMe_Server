package org.greenbyme.angelhack.service.dto.missionInfo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.missionInfo.MissionInfo;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InProgressResponseDto {

    private Long missionInfoId;
    private String missionTitle;
    private int finishCount;
    private int progress;
    private int remainPeriod;
    private int manyPeople;

    public InProgressResponseDto(MissionInfo missionInfo) {
        this.missionInfoId = missionInfo.getId();
        this.missionTitle = missionInfo.getMission().getSubject();
        this.finishCount = missionInfo.getFinishCount();
        this.progress = missionInfo.getProgress();
        this.remainPeriod = missionInfo.getRemainPeriod();
        this.manyPeople = missionInfo.getMission();
    }
}
