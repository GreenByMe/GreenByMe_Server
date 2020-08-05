package org.greenbyme.angelhack.service.dto.mission;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.mission.MissionCertificateCount;
import org.greenbyme.angelhack.domain.mission.MissionCertificationMethod;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MissionCertificationMethodRequestDto {

    private String title;
    private String text;
    private MissionCertificateCount missionCertificateCount;

    public MissionCertificationMethodRequestDto(MissionCertificationMethod missionCertificationMethod) {
        this.title = missionCertificationMethod.getTitle();
        this.text = missionCertificationMethod.getText();
        this.missionCertificateCount = missionCertificationMethod.getMissionCertificateCount();
    }
/*
    public MissionCertificationMethod toEntity(){
        return MissionCertificationMethod.builder()
                .title(title)
                .text(text)
                .missionCertificateCount(missionCertificateCount)
                .build();
    }*/
}
