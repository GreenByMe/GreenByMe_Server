package org.greenbyme.angelhack.service.dto.mission;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.mission.MissionCertificationMethod;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MissionCertificationMethodRequestDto {

    private String title;
    private String text;

    public MissionCertificationMethodRequestDto(MissionCertificationMethod missionCertificationMethod) {
        this.title = missionCertificationMethod.getTitle();
        this.text = missionCertificationMethod.getText();
    }
}
