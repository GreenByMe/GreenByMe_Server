package org.greenbyme.angelhack.service.dto.mission;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.mission.Mission;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MissionDeleteDto {
    private Long id;
    private String message;

    public MissionDeleteDto(Mission mission){
        id = mission.getId();
        message = "미션 삭제 완료";
    }
}
