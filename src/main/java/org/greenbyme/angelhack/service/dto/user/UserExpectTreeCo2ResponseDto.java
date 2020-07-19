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
        nickNameSentence = user.getNickname();
        expectTreeSentence = "지금까지 벌써 <br><font color=\"#26B679\">" + Math.round(user.getExpectTree()*100)/100.0 +"개의 나무를</font><br>심으셨군요!";
        this.expectCo2 = user.getExpectCo2();
        this.expectTree = user.getExpectTree();
        this.progressMissions = progressMissions;
        this.progressRates = progressRates;
    }
}
