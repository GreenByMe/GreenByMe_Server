package org.greenbyme.angelhack.service.dto.mission;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.mission.Mission;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MissionSaveResponseDto {

    private Long id;
    private String message;

    public MissionSaveResponseDto(Mission mission){
        id = mission.getId();
        message = "미션이 등록 되었습니다.";
    }
}
