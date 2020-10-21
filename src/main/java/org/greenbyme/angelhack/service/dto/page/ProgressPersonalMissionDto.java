package org.greenbyme.angelhack.service.dto.page;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.Category.Category;
import org.greenbyme.angelhack.domain.personalmission.PersonalMission;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProgressPersonalMissionDto {

    private Long personalMissionId;
    private String subject;
    private Category category;
    private String certifiaciontTest;
    private int progressRates;
    private int current;
    private int finishCount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String pictureUrl;

    public ProgressPersonalMissionDto(PersonalMission personalMission) {
        this.personalMissionId = personalMission.getId();
        this.subject = personalMission.getMission().getSubject();
        this.category = personalMission.getMission().getCategory();
        this.certifiaciontTest = personalMission.getMission().getMissionCertificationMethod().getText();
        this.startDate = personalMission.getCreatedDate();
        this.endDate = personalMission.getCreatedDate().plusDays(personalMission.getMission().getDayCategory().getDay());
        this.current = personalMission.getProgress();
        this.finishCount = personalMission.getFinishCount();
        this.progressRates = current / finishCount * 100;
        this.pictureUrl = personalMission.getMission().getPictureUrl();
    }
}
