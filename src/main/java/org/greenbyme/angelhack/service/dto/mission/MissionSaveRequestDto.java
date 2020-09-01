package org.greenbyme.angelhack.service.dto.mission;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.Category.Category;
import org.greenbyme.angelhack.domain.Category.DayCategory;
import org.greenbyme.angelhack.domain.mission.MissionCertificateCount;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MissionSaveRequestDto {

    @NotEmpty
    private String title;
    @NotEmpty
    private String subject;
    @NotEmpty
    private String description;
    private Double expectCo2;
    private Category category;
    private DayCategory dayCategory;
    @NotEmpty
    private String missionCertificationTitle;
    @NotEmpty
    private String missionCertificationText;
    private MissionCertificateCount missionCertificateCount;

    public MissionSaveRequestDto(String title, String subject, String description, double expectCo2, Category category, DayCategory dayCategory, String missionCertificationTitle, String missionCertificationText, MissionCertificateCount missionCertificateCount) {
        this.title = title;
        this.subject = subject;
        this.description = description;
        this.expectCo2 = expectCo2;
        this.category = category;
        this.dayCategory = dayCategory;
        this.missionCertificationTitle = missionCertificationTitle;
        this.missionCertificationText = missionCertificationText;
        this.missionCertificateCount = missionCertificateCount;
    }
}
