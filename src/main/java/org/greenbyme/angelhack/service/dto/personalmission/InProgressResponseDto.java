package org.greenbyme.angelhack.service.dto.personalmission;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.personalmission.PersonalMission;
import org.greenbyme.angelhack.domain.personalmission.RemainPeriod;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InProgressResponseDto implements Comparable<InProgressResponseDto> {

    private Long personalMissionId;
    private Long missionId;
    private String missionTitle;
    private int finishCount;
    private int progress;
    private Long manyPeople;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private RemainPeriod remainPeriod;
    private String missionPictureUrl;

    public InProgressResponseDto(PersonalMission personalMission, Long manyPeople) {
        this.personalMissionId = personalMission.getId();
        this.missionId = personalMission.getMission().getId();
        this.missionTitle = personalMission.getMission().getTitle();
        this.finishCount = personalMission.getFinishCount();
        this.progress = personalMission.getProgress();
        this.manyPeople = manyPeople;
        this.startDate = personalMission.getCreatedDate();
        this.endDate = personalMission.getCreatedDate().plusDays(personalMission.getMission().getDayCategory().getDay());
        this.remainPeriod = personalMission.getRemainPeriod();
        this.missionPictureUrl = personalMission.getMission().getPictureUrl();
    }

    @Override
    public int compareTo(InProgressResponseDto o) {
        return -this.startDate.compareTo(o.startDate);
    }
}
