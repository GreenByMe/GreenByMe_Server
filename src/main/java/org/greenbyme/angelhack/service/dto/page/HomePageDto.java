package org.greenbyme.angelhack.service.dto.page;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.personalmission.PersonalMissionStatus;
import org.greenbyme.angelhack.domain.user.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HomePageDto {

    private String nickName;
    private String treeSentence;
    private Long progressCampaign;
    private double expectedCO2;
    private double expectedTree;
    private long progressRates;
    private PersonalMissionPageDto pageDto;

    public HomePageDto(User user, long progressRates, PersonalMissionPageDto pageDto) {
        this.nickName = user.getNickname();
        this.treeSentence = "지금까지 벌써 <br><font color=\"#26B679\">" + Math.round(user.getExpectTree() * 100) / 100.0 + "개의 나무를</font><br>심으셨군요!";
        this.progressCampaign = pageDto.getPersonalMissionList().stream()
                .filter(p->p.getStatus().equals(PersonalMissionStatus.IN_PROGRESS.name()))
                .count();
        this.expectedCO2 = user.getExpectCo2();
        this.expectedTree = user.getExpectTree();
        this.progressRates = progressRates;
        this.pageDto = pageDto;
    }
}
