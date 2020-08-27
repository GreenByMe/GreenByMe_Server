package org.greenbyme.angelhack.service.dto.page;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.post.Post;
import org.greenbyme.angelhack.domain.user.User;
import org.greenbyme.angelhack.service.dto.personalmission.PersonalMissionDetailDto;
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
    private List<PersonalMissionDetailDto> personalMissions;
    private List<PostDetailResponseDto> posts;

    public MyPageDto(User user, Long passMissionCount, List<Post> posts) {
        this.userId = user.getId();
        this.email = user.getEmail();
        this.nickName = user.getNickname();
        this.personalMissions = user.getPersonalMissionList().stream()
                .map(PersonalMissionDetailDto::new)
                .collect(Collectors.toList());
        this.posts = posts.stream()
                .map(p -> new PostDetailResponseDto(p, true))
                .collect(Collectors.toList());
        this.pictureUrl = user.getPhoto();
        this.expectCo2 = user.getExpectCo2();
        this.expectTree = user.getExpectTree();
        this.passMissionCount = passMissionCount;
    }
}
