package org.greenbyme.angelhack.domain.mission;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.Category.Category;
import org.greenbyme.angelhack.domain.Category.DayCategory;
import org.greenbyme.angelhack.domain.baseEntity.BaseTimeEntity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Mission extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "mission_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private DayCategory dayCategory;

    @Embedded
    private MissionCertificationMethod missionCertificationMethod;

    private String subject;
    private String description;

    private double expectTree;
    private double expectCo2;

    private Long passCandidatesCount;

    @Builder
    public Mission(Category category, String subject, String description, DayCategory dayCategory,
                   MissionCertificationMethod missionCertificationMethod, double expectCo2) {
        this.category = category;
        this.subject = subject;
        this.description = description;
        this.dayCategory = dayCategory;
        this.missionCertificationMethod = missionCertificationMethod;
        this.expectCo2 = expectCo2;
        this.expectTree = expectCo2 / 3.71;
        this.passCandidatesCount = 0L;
    }

    public void changeCategory(Category category) {
        this.category = category;
    }

    public void changeDayCategory(DayCategory dayCategory) {
        this.dayCategory = dayCategory;
    }

    public void changeMissionCertificationMethod(MissionCertificationMethod missionCertificationMethod) {
        this.missionCertificationMethod = missionCertificationMethod;
    }

    public void addPassCandidates() {
        this.passCandidatesCount += 1;
    }
}

