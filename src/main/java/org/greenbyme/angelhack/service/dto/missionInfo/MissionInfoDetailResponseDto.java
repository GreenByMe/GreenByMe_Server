package org.greenbyme.angelhack.service.dto.missionInfo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.missionInfo.MissionInfo;
import org.greenbyme.angelhack.service.dto.mission.MissionDetailsDto;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MissionInfoDetailResponseDto {

    private String userNickName;
    private MissionDetailsDto missionDetailsDto;
    private int current;
    private int remainPeriod;

    @Builder
    public MissionInfoDetailResponseDto(MissionInfo missionInfo) {
        this.userNickName = missionInfo.getUser().getNickname();
        this.missionDetailsDto = new MissionDetailsDto(missionInfo.getMission());
        this.current = missionInfo.getCurrent();
        this.remainPeriod = missionInfo.getRemainPeriod();
    }
}
