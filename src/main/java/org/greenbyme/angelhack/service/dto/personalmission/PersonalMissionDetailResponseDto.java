package org.greenbyme.angelhack.service.dto.personalmission;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.personalmission.PersonalMission;
import org.greenbyme.angelhack.domain.personalmission.PersonalMissionStatus;
import org.greenbyme.angelhack.domain.personalmission.RemainPeriod;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonalMissionDetailResponseDto {

    private String userNickName;
    private Long mission_id;
    private int finishCount;
    private int progress;
    private RemainPeriod remainPeriod;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private PersonalMissionStatus personalMissionStatus;

    @Builder
    public PersonalMissionDetailResponseDto(PersonalMission personalMission) {
        this.userNickName = personalMission.getUser().getNickname();
        this.mission_id =personalMission.getMission().getId();
        this.finishCount = personalMission.getFinishCount();
        this.progress = personalMission.getProgress();
        this.remainPeriod = personalMission.getRemainPeriod();
        this.startDate = personalMission.getCreatedDate();
        this.endDate = personalMission.getCreatedDate().plusDays(personalMission.getMission().getDayCategory().getDay());
        this.personalMissionStatus = personalMission.getPersonalMissionStatus();
    }
}
