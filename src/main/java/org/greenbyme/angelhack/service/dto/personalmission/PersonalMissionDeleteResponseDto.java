package org.greenbyme.angelhack.service.dto.personalmission;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.personalmission.PersonalMission;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonalMissionDeleteResponseDto {

    private Long personalMission_id;
    private String message;

    @Builder
    public PersonalMissionDeleteResponseDto(PersonalMission personalMission){
        personalMission_id = personalMission.getId();
        message = "사용자 미션 삭제 완료";
    }
}
