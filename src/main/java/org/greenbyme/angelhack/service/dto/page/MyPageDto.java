package org.greenbyme.angelhack.service.dto.page;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.user.User;
import org.greenbyme.angelhack.service.dto.missionInfo.MissionInfoDetailDto;
import org.greenbyme.angelhack.service.dto.post.PostDetailResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyPageDto {

    private Long userId;
    private String email;
    private String nickName;
    private String pictureUrl;
    private double expectCo2;
    private double expectTree;
    private Long passMissionCount;
    private List<MissionInfoDetailDto> missionInfoList;
    private List<PostDetailResponseDto> posts;

    public MyPageDto(User user, Long passMissionCount) {
        this.userId = user.getId();
        this.email = user.getEmail();
        this.nickName = user.getNickname();
        this.missionInfoList = user.getMissionInfoList().stream()
                .map(MissionInfoDetailDto::new)
                .collect(Collectors.toList());
        this.posts = user.getPostList().stream()
                .map(PostDetailResponseDto::new)
                .collect(Collectors.toList());
        this.pictureUrl = user.getPhoto();
        this.expectCo2 = user.getExpectCo2();
        this.expectTree = user.getExpectTree();
        this.passMissionCount = passMissionCount;
    }
}
