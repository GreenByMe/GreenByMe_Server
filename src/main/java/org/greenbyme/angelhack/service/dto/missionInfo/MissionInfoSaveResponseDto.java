package org.greenbyme.angelhack.service.dto.missionInfo;

import org.greenbyme.angelhack.domain.missionInfo.MissionInfo;

public class MissionInfoSaveResponseDto {

    private Long id;
    private String message;

    public MissionInfoSaveResponseDto(MissionInfo missionInfo) {
        this.id = missionInfo.getId();
        this.message = "사용자 미션 등록 완료";
    }
}
