package org.greenbyme.angelhack.service.dto.page;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.mission.Mission;
import org.greenbyme.angelhack.domain.missionInfo.MissionInfo;
import org.greenbyme.angelhack.domain.user.User;
import org.greenbyme.angelhack.service.dto.mission.MissionPopularResponseDto;
import org.greenbyme.angelhack.service.dto.missionInfo.InProgressResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HomePageDto {

    private String nickName;
    private String treeSentence;
    private int progressCampaign;
    private double expectedCO2;
    private double expectedTree;
    private long progressRates;
    private List<InProgressResponseDto> progressResponseDtoList;
    private Page<PopularMissionResponseDto> popularMissionResponseDtoList;

    public HomePageDto(User user, long progressRates, List<InProgressResponseDto> progressMissions, Page<PopularMissionResponseDto> popularMissions) {
        this.nickName = user.getNickname();
        this.treeSentence = "지금까지 벌써 <br> \"<p style=\\\"color:#26B679;\\\">" + (int) Math.floor(user.getExpectTree()) + "개의 나무를 </p> <br>" + "심으셨군요!";
        this.progressCampaign = progressMissions.size();
        this.expectedCO2 = user.getExpectCo2();
        this.expectedTree = user.getExpectTree();
        this.progressRates = progressRates;
        this.progressResponseDtoList = progressMissions;
        this.popularMissionResponseDtoList = popularMissions;
    }
}