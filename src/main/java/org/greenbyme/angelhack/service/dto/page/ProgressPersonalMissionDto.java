package org.greenbyme.angelhack.service.dto.page;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.category.Category;
import org.greenbyme.angelhack.domain.personalmission.PersonalMission;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProgressPersonalMissionDto {

    private Long personalMissionId;
    private String subject;
    private Category category;
    private String certificationText;
    private int progressRates;
    private int current;
    private int finishCount;
    private boolean isAbleCertificate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String pictureUrl;

    public ProgressPersonalMissionDto(PersonalMission personalMission, boolean isAbleCertificate) {
        this.personalMissionId = personalMission.getId();
        this.subject = personalMission.getMission().getSubject();
        this.category = personalMission.getMission().getCategory();
        this.certificationText = personalMission.getMission().getMissionCertificationMethod().getText();
        this.startDate = personalMission.getCreatedDate();
        this.endDate = personalMission.getCreatedDate().plusDays(personalMission.getMission().getDayCategory().getDay());
        this.current = personalMission.getProgress();
        this.finishCount = personalMission.getFinishCount();
        this.progressRates = current / finishCount * 100;
        this.isAbleCertificate = isAbleCertificate;
        this.pictureUrl = personalMission.getMission().getPictureUrl();
    }
}
