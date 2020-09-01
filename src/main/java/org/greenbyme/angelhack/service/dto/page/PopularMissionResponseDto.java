package org.greenbyme.angelhack.service.dto.page;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.mission.Mission;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PopularMissionResponseDto implements Comparable<PopularMissionResponseDto> {

    private Long missionId;
    private String subject;
    private Long progressCount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String pictureUrl;

    public PopularMissionResponseDto(Mission mission, Long progressCount) {
        this.missionId = mission.getId();
        this.subject = mission.getSubject();
        this.progressCount = progressCount;
        this.startDate = LocalDateTime.now();
        this.endDate = LocalDateTime.now().plusDays(mission.getDayCategory().getDay());
        this.pictureUrl = mission.getPictureUrl();
    }

    @Override
    public int compareTo(PopularMissionResponseDto o) {
        return -this.progressCount.compareTo(o.progressCount);
    }
}
