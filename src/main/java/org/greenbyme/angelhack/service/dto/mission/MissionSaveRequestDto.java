package org.greenbyme.angelhack.service.dto.mission;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.mission.Mission;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MissionSaveRequestDto {

    private String subject;
    private String description;
    private double expectCo2;
    private String pictureUrl;
    private MissionCertificationMethodRequestDto missionCertificationMethodRequestDto;

    public Mission toEntity(){
        return Mission.builder()
                .subject(subject)
                .description(description)
                .pictureUrl(pictureUrl)
                .expectCo2(expectCo2)
                .build();
    }
}
