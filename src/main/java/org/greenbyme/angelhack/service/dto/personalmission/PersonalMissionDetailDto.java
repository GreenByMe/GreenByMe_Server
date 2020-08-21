package org.greenbyme.angelhack.service.dto.personalmission;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.personalmission.PersonalMission;
import org.greenbyme.angelhack.domain.personalmission.PersonalMissionStatus;
import org.greenbyme.angelhack.domain.personalmission.RemainPeriod;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PersonalMissionDetailDto {

    private Long personalMission_id;
    private Long userId;
    private Long missionId;
    private PersonalMissionStatus personalMissionStatus;

    private int finishCount;
    private int progress;
    private RemainPeriod remainPeriod;
    private String missionPictureUrl;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public PersonalMissionDetailDto(PersonalMission personalMission) {
        this.personalMission_id = personalMission.getId();
        this.userId = personalMission.getUser().getId();
        this.missionId = personalMission.getMission().getId();
        this.personalMissionStatus = personalMission.getPersonalMissionStatus();
        this.finishCount = personalMission.getFinishCount();
        this.progress = personalMission.getProgress();
        this.remainPeriod = personalMission.getRemainPeriod();
        this.missionPictureUrl = personalMission.getMission().getPictureUrl();
        this.startDate = personalMission.getCreatedDate();
        this.endDate = personalMission.getCreatedDate().plusDays(personalMission.getMission().getDayCategory().getDay());
    }
}
