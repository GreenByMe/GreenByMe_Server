package org.greenbyme.angelhack.service.dto.personalmission;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.personalmission.PersonalMission;
import org.greenbyme.angelhack.domain.personalmission.PersonalMissionStatus;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FinishedResponseDto implements Comparable<FinishedResponseDto> {

    private Long personalMissionId;
    private Long missionId;
    private String missionTitle;
    private Long manyPeople;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String pictureUrl;
    private PersonalMissionStatus status;

    public FinishedResponseDto(PersonalMission personalMission, Long manyPeople) {
        this.personalMissionId = personalMission.getId();
        this.missionId = personalMission.getMission().getId();
        this.missionTitle = personalMission.getMission().getTitle();
        this.manyPeople = manyPeople;
        this.startDate = personalMission.getCreatedDate();
        this.endDate = personalMission.getCreatedDate().plusDays(personalMission.getMission().getDayCategory().getDay());
        this.pictureUrl = personalMission.getMission().getPictureUrl();
        this.status = personalMission.getPersonalMissionStatus();
    }

    @Override
    public int compareTo(FinishedResponseDto o) {
        return -this.startDate.compareTo(o.startDate);
    }
}
