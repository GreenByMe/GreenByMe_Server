package org.greenbyme.angelhack.service.dto.page;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.mission.Mission;

@Getter
@NoArgsConstructor
public class PopularMissionResponseDto {

    private Long missionId;
    private String subject;
    private Long progressCount;

    public PopularMissionResponseDto(Mission mission, Long progressCount) {
        this.missionId = mission.getId();
        this.subject = mission.getSubject();
        this.progressCount = progressCount;
    }
}
