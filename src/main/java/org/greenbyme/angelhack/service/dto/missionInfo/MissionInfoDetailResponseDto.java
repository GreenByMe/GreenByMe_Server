package org.greenbyme.angelhack.service.dto.missionInfo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.missionInfo.MissionInfo;
import org.greenbyme.angelhack.domain.missionInfo.MissionInfoStatus;
import org.greenbyme.angelhack.service.dto.mission.MissionDetailsDto;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MissionInfoDetailResponseDto {

    private String userNickName;
    private MissionDetailsDto missionDetailsDto;
    private int finishCount;
    private int progress;
    private int remainPeriod;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private MissionInfoStatus missionInfoStatus;

    @Builder
    public MissionInfoDetailResponseDto(MissionInfo missionInfo) {
        this.userNickName = missionInfo.getUser().getNickname();
        this.missionDetailsDto = new MissionDetailsDto(missionInfo.getMission());
        this.finishCount = missionInfo.getFinishCount();
        this.progress = missionInfo.getProgress();
        this.remainPeriod = missionInfo.getRemainPeriod();
        this.startDate = missionInfo.getCreatedDate();
        this.endDate = missionInfo.getCreatedDate().plusDays(missionInfo.getMission().getDayCategory().getDay());
        this.missionInfoStatus = missionInfo.getMissionInfoStatus();
    }
}
