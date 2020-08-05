package org.greenbyme.angelhack.service.dto.mission;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.Category.Category;
import org.greenbyme.angelhack.domain.Category.DayCategory;
import org.greenbyme.angelhack.domain.mission.Mission;
import org.greenbyme.angelhack.domain.mission.MissionCertificateCount;
import org.greenbyme.angelhack.domain.mission.MissionCertificationMethod;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MissionSaveRequestDto {

    private String subject;
    private String description;
    private double expectCo2;
    private Category category;
    private DayCategory dayCategory;

    private String missionCertificationTitle;
    private String missionCertificationText;
    private MissionCertificateCount missionCertificateCount;

    //private MissionCertificationMethodRequestDto missionCertificationMethodRequestDto;

    public MissionSaveRequestDto(String subject, String description, double expectCo2, Category category, DayCategory dayCategory, String missionCertificationTitle, String missionCertificationText, MissionCertificateCount missionCertificateCount) {
        this.subject = subject;
        this.description = description;
        this.expectCo2 = expectCo2;
        this.category = category;
        this.dayCategory = dayCategory;
        this.missionCertificationTitle = missionCertificationTitle;
        this.missionCertificationText = missionCertificationText;
        this.missionCertificateCount = missionCertificateCount;
    }

/*    public MissionSaveRequestDto(String subject, String description, double expectCo2, Category category, DayCategory dayCategory, MissionCertificationMethodRequestDto missionCertificationMethodRequestDto) {
        this.subject = subject;
        this.description = description;
        this.expectCo2 = expectCo2;
        this.category = category;
        this.dayCategory = dayCategory;
        this.missionCertificationMethodRequestDto = missionCertificationMethodRequestDto;
    }*/

/*    public Mission toEntity(){
        return Mission.builder()
                .subject(subject)
                .description(description)
                .expectCo2(expectCo2)
                .category(category)
                .dayCategory(dayCategory)
                .missionCertificationMethod(missionCertificationMethodRequestDto.toEntity())
                .build();
    }*/
}
