package org.greenbyme.angelhack.service.dto.missionInfo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.missionInfo.MissionInfo;
import org.greenbyme.angelhack.domain.missionInfo.MissionInfoStatus;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MissionInfobyUserDto {

    private Long missionInfoId;
    private Long missionId;
    private String missionPictureUrl;
    private MissionInfoStatus missionInfoStatus;

    private int finishCount;
    private int progress;
    private int remainPeriod;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public MissionInfobyUserDto(MissionInfo missionInfo) {
        this.missionInfoId = missionInfo.getId();
        this.missionId = missionInfo.getMission().getId();
        this.missionPictureUrl = missionInfo.getMission().getPictureUrl();
        this.missionInfoStatus = missionInfo.getMissionInfoStatus();
        this.finishCount = missionInfo.getFinishCount();
        this.progress = missionInfo.getProgress();
        this.remainPeriod = missionInfo.getRemainPeriod();
        this.startDate = missionInfo.getCreatedDate();
        this.endDate = missionInfo.getCreatedDate().plusDays(missionInfo.getMission().getDayCategory().getDay());
    }
}