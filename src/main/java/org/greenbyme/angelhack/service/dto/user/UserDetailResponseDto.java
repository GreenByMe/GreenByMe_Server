package org.greenbyme.angelhack.service.dto.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.missionInfo.MissionInfo;
import org.greenbyme.angelhack.domain.post.Post;
import org.greenbyme.angelhack.domain.user.User;
import org.greenbyme.angelhack.service.dto.missionInfo.MissionInfoDetailDto;
import org.greenbyme.angelhack.service.dto.post.PostDetailResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDetailResponseDto {

    private Long userId;
    private String email;
    private String nickName;
    private List<MissionInfoDetailDto> missionInfoList;
    private List<PostDetailResponseDto> posts;

    public UserDetailResponseDto(User user) {
        this.userId = user.getId();
        this.email = user.getEmail();
        this.nickName = user.getNickname();
        this.missionInfoList = user.getMissionInfoList().stream()
                .map(MissionInfoDetailDto::new)
                .collect(Collectors.toList());
        this.posts = user.getPostList().stream()
                .map(PostDetailResponseDto::new)
                .collect(Collectors.toList());
    }
}
