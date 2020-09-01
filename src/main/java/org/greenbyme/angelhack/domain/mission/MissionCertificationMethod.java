package org.greenbyme.angelhack.domain.mission;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"title", "text", "missionCertificateCount"})
public class MissionCertificationMethod {

    @NotEmpty @Column(name = "title", nullable = false)
    private String title;

    @NotEmpty @Column(name = "text", nullable = false)
    private String text;

    @Enumerated(EnumType.STRING)
    private MissionCertificateCount missionCertificateCount;

    @Builder
    public MissionCertificationMethod(String title, String text, MissionCertificateCount missionCertificateCount) {
        this.title = title;
        this.text = text;
        this.missionCertificateCount = missionCertificateCount;
    }
}
