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
public class PersonalMissionHomePageDto {

    private Long personalMissionId;
    private Long missionId;
    private String missionTitle;
    private Integer finishCount;
    private Integer progress;
    private Long manyPeople;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private RemainPeriod remainPeriod;
    private String pictureUrl;
    private String status;

    public PersonalMissionHomePageDto(PersonalMission personalMission, Long manyPeople) {
        this.personalMissionId = personalMission.getId();
        this.missionId = personalMission.getMission().getId();
        this.missionTitle = personalMission.getMission().getTitle();
        this.manyPeople = manyPeople;
        this.status = personalMission.getPersonalMissionStatus().name();
        this.pictureUrl = personalMission.getMission().getPictureUrl();
        this.startDate = personalMission.getCreatedDate();
        this.endDate = personalMission.getCreatedDate().plusDays(personalMission.getMission().getDayCategory().getDay());
        if (status.equals(PersonalMissionStatus.IN_PROGRESS.name())) {
            this.finishCount = personalMission.getFinishCount();
            this.progress = personalMission.getProgress();
            this.remainPeriod = personalMission.getRemainPeriod();
        }
    }
}
