package org.greenbyme.angelhack.service.dto.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.user.User;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserExpectTreeCo2ResponseDto {

    private String nickNameSentence;
    private String expectTreeSentence;

    private double expectCo2;
    private double expectTree;
    private long progressMissions;
    private long progressRates;

    public UserExpectTreeCo2ResponseDto(User user, long progressMissions, long progressRates) {
        nickNameSentence = "안녕하세요 " + user.getNickname() + "님";
        expectTreeSentence = "지금까지 " + "<p style=\"color:MediumSeaGreen;\">" + (int) Math.floor(user.getExpectTree()) + "개의 나무를 </p> <br>" + "심으셨군요!";
        this.expectCo2 = user.getExpectCo2();
        this.expectTree = user.getExpectTree();
        this.progressMissions = progressMissions;
        this.progressRates = progressRates;
    }
}
