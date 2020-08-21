package org.greenbyme.angelhack.service.dto.personalmission;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.personalmission.PersonalMission;
import org.greenbyme.angelhack.domain.personalmission.PersonalMissionStatus;
import org.greenbyme.angelhack.domain.personalmission.RemainPeriod;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonalMissionByUserDto {

    private Long personalMission_id;
    private Long missionId;
    private String missionPictureUrl;
    private PersonalMissionStatus personalMissionStatus;

    private int finishCount;
    private int progress;
    private RemainPeriod remainPeriod;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public PersonalMissionByUserDto(PersonalMission personalMission) {
        this.personalMission_id = personalMission.getId();
        this.missionId = personalMission.getMission().getId();
        this.missionPictureUrl = personalMission.getMission().getPictureUrl();
        this.personalMissionStatus = personalMission.getPersonalMissionStatus();
        this.finishCount = personalMission.getFinishCount();
        this.progress = personalMission.getProgress();
        this.remainPeriod = personalMission.getRemainPeriod();
        this.startDate = personalMission.getCreatedDate();
        this.endDate = personalMission.getCreatedDate().plusDays(personalMission.getMission().getDayCategory().getDay());
    }
}