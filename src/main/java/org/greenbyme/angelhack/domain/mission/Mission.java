package org.greenbyme.angelhack.domain.mission;

import lombok.*;
import org.greenbyme.angelhack.domain.category.Category;
import org.greenbyme.angelhack.domain.category.DayCategory;
import org.greenbyme.angelhack.domain.DomainListener;
import org.greenbyme.angelhack.domain.baseEntity.BaseTimeEntity;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(DomainListener.class)
@ToString(of = {"id","title","subject", "missionCertificationMethod" })
public class Mission extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private DayCategory dayCategory;

    @Embedded
    private MissionCertificationMethod missionCertificationMethod;

    @Column(name = "mission_title")
    private String title;
    private String subject;
    private String description;

    private double expectTree;
    private double expectCo2;

    private double cumulativeCo2;
    private double cumulativeTree;

    private Long passCandidatesCount;
    private String pictureUrl;

    @Builder
    public Mission(Category category, String title, String subject, String description, DayCategory dayCategory,
                   MissionCertificationMethod missionCertificationMethod, double expectCo2, String pictureUrl) {
        this.category = category;
        this.title = title;
        this.subject = subject;
        this.description = description;
        this.dayCategory = dayCategory;
        this.missionCertificationMethod = missionCertificationMethod;
        this.expectCo2 = expectCo2;
        this.expectTree = expectCo2 / 3.71;
        this.cumulativeCo2 = 0;
        this.cumulativeTree = 0;
        this.passCandidatesCount = 0L;
        this.pictureUrl = pictureUrl;
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

    public void changePictureUrl(String pictureUrl){
        this.pictureUrl = pictureUrl;
    }

    public void addPassCandidates() {
        this.passCandidatesCount += 1;
    }

    public void addCumulativeCo2Tree() {
        this.cumulativeCo2 += expectCo2;
        this.cumulativeTree = cumulativeCo2 / 3.71;
    }
}
