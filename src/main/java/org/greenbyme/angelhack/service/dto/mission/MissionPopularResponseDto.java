package org.greenbyme.angelhack.service.dto.mission;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.Category.Category;
import org.greenbyme.angelhack.domain.mission.Mission;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MissionPopularResponseDto {

    private Long missionId;
    @Enumerated(EnumType.STRING)
    private Category category;
    private String subject;
    private Long passCandidatesCount;

    public MissionPopularResponseDto(Mission mission) {
        this.missionId = mission.getId();
        this.category = mission.getCategory();
        this.subject = mission.getSubject();
        this.passCandidatesCount = mission.getPassCandidatesCount();
    }
}
