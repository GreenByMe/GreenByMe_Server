package org.greenbyme.angelhack.service.dto.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.user.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserHomePageDetailDto {
    private String nickName;
    private String treeSentence;
    private double expectedCO2;
    private double expectedTree;
    private long progressRates;

    public UserHomePageDetailDto(User user, long progressRates) {
        this.nickName = user.getNickname();
        this.treeSentence = "지금까지 벌써 <br><b><font color=\"#26B679\">" + Math.round(user.getExpectTree() * 100) / 100.0 + "개의 나무</font></b><br>를심으셨군요!";
        this.expectedCO2 = user.getExpectCo2();
        this.expectedTree = user.getExpectTree();
        this.progressRates = progressRates;
    }
}
