package org.greenbyme.angelhack.service.dto.mission;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.Category.Category;
import org.greenbyme.angelhack.domain.Category.DayCategory;
import org.greenbyme.angelhack.domain.mission.Mission;
import org.greenbyme.angelhack.domain.mission.MissionCertificationMethod;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MissionSaveRequestDto {

    private String subject;
    private String description;
    private MissionCertificationMethodRequestDto missionCertificationMethodRequestDto;

    public Mission toEntity(){
        return Mission.builder()
                .subject(subject)
                .description(description)
                .build();
    }
}
