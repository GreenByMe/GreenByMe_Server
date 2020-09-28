package org.greenbyme.angelhack.service.dto.page;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.mission.Mission;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PopularMissionHomePageResponseDto {
    private Long missionId;
    private String subject;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long passCandidatesCount;
    private String pictureUrl;

    public PopularMissionHomePageResponseDto(Mission mission) {
        this.missionId = mission.getId();
        this.subject = mission.getSubject();
        this.startDate = LocalDateTime.now();
        this.endDate = LocalDateTime.now().plusDays(mission.getDayCategory().getDay());
        this.passCandidatesCount = mission.getPassCandidatesCount();
        this.pictureUrl = mission.getPictureUrl();
    }

}
