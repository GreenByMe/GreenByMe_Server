package org.greenbyme.angelhack.service.dto.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.user.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserExpectTreeCo2ResponseDto {

    private String nickName;
    private String expectTree;

    private String expectCo2;

    public UserExpectTreeCo2ResponseDto(User user){
        nickName = "안녕하세요 "+user.getNickname()+"님";
        expectTree = "지금까지 "+"<p style=\"color:MediumSeaGreen;\">"+ (int)user.getExpectTree() +"개의 나무를 </p> <br>"+"심으셨군요!";
        expectCo2 = "줄인 탄소양: " + user.getExpectCo2() +"Kg/CO<sub>2</sub>";
    }
}
