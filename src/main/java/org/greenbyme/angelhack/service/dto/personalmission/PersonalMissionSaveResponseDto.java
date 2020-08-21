package org.greenbyme.angelhack.service.dto.personalmission;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.personalmission.PersonalMission;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonalMissionSaveResponseDto {

    private Long id;
    private String message;

    @Builder
    public PersonalMissionSaveResponseDto(PersonalMission personalMission) {
        this.id = personalMission.getId();
        this.message = "사용자 미션 등록 완료";
    }
}
