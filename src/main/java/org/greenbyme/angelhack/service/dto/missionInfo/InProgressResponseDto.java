package org.greenbyme.angelhack.service.dto.missionInfo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.missionInfo.MissionInfo;
import org.greenbyme.angelhack.domain.missionInfo.RemainPeriod;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InProgressResponseDto {

    private Long missionInfoId;
    private String missionTitle;
    private int finishCount;
    private int progress;
    private Long manyPeople;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private RemainPeriod remainPeriod;
    private String pictureUrl;

    public InProgressResponseDto(MissionInfo missionInfo, Long manyPeople) {
        this.missionInfoId = missionInfo.getId();
        this.missionTitle = missionInfo.getMission().getTitle();
        this.finishCount = missionInfo.getFinishCount();
        this.progress = missionInfo.getProgress();
        this.manyPeople = manyPeople;
        this.startDate = missionInfo.getCreatedDate();
        this.endDate = missionInfo.getCreatedDate().plusDays(missionInfo.getMission().getDayCategory().getDay());
        this.remainPeriod = missionInfo.getRemainPeriod();
        this.pictureUrl = missionInfo.getMission().getPictureUrl();
    }
}
