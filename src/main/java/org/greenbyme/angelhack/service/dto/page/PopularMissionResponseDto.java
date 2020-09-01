package org.greenbyme.angelhack.service.dto.page;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.mission.Mission;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PopularMissionResponseDto {

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
}
