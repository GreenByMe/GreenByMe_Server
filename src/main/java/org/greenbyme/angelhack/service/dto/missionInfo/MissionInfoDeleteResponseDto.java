package org.greenbyme.angelhack.service.dto.missionInfo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.missionInfo.MissionInfo;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MissionInfoDeleteResponseDto {

    private Long missionInfoId;
    private String message;

    @Builder
    public MissionInfoDeleteResponseDto(MissionInfo missionInfo){
        missionInfoId = missionInfo.getId();
        message = "사용자 미션 삭제 완료";
    }
}
