package org.greenbyme.angelhack.service.dto.missionInfo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.missionInfo.MissionInfo;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MissionInfoSaveResponseDto {

    private Long id;
    private String message;

    @Builder
    public MissionInfoSaveResponseDto(MissionInfo missionInfo) {
        this.id = missionInfo.getId();
        this.message = "사용자 미션 등록 완료";
    }
}
