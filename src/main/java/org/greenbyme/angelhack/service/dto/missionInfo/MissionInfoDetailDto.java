package org.greenbyme.angelhack.service.dto.missionInfo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.missionInfo.MissionInfo;
import org.greenbyme.angelhack.domain.missionInfo.MissionInfoStatus;
import org.greenbyme.angelhack.domain.missionInfo.RemainPeriod;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MissionInfoDetailDto {

    private Long missionInfoId;
    private Long userId;
    private Long missionId;
    private MissionInfoStatus missionInfoStatus;

    private int finishCount;
    private int progress;
    private RemainPeriod remainPeriod;
    private String missionPictureUrl;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public MissionInfoDetailDto(MissionInfo missionInfo) {
        this.missionInfoId = missionInfo.getId();
        this.userId = missionInfo.getUser().getId();
        this.missionId = missionInfo.getMission().getId();
        this.missionInfoStatus = missionInfo.getMissionInfoStatus();
        this.finishCount = missionInfo.getFinishCount();
        this.progress = missionInfo.getProgress();
        this.remainPeriod = missionInfo.getRemainPeriod();
        this.missionPictureUrl = missionInfo.getMission().getPictureUrl();
        this.startDate = missionInfo.getCreatedDate();
        this.endDate = missionInfo.getCreatedDate().plusDays(missionInfo.getMission().getDayCategory().getDay());
    }
}
