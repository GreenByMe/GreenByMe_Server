package org.greenbyme.angelhack.service.dto.missionInfo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.missionInfo.MissionInfo;
import org.greenbyme.angelhack.domain.missionInfo.MissionInfoStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MissionInfobyUserDto {

    private Long missionInfoId;
    private Long missionId;
    private MissionInfoStatus missionInfoStatus;

    private int finishCount;
    private int progress;
    private int remainPeriod;

    public MissionInfobyUserDto(MissionInfo missionInfo) {
        this.missionInfoId = missionInfo.getId();
        this.missionId = missionInfo.getMission().getId();
        this.missionInfoStatus = missionInfo.getMissionInfoStatus();
        this.finishCount = missionInfo.getFinishCount();
        this.progress = missionInfo.getProgress();
        this.remainPeriod = missionInfo.getRemainPeriod();
    }
}