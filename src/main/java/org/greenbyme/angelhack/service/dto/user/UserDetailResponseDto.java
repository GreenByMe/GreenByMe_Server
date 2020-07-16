package org.greenbyme.angelhack.service.dto.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.missionInfo.MissionInfo;
import org.greenbyme.angelhack.domain.post.Post;
import org.greenbyme.angelhack.domain.user.User;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDetailResponseDto {

    private Long userId;
    private String email;
    private String nickName;
    private List<MissionInfo> missionInfoList = new ArrayList<>();
    private List<Post> posts = new ArrayList<>();

    public UserDetailResponseDto(User user) {

    }
}
