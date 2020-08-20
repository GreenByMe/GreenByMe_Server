package org.greenbyme.angelhack.service.dto.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.user.User;
import org.greenbyme.angelhack.service.dto.personalmission.PersonalMissionDetailDto;
import org.greenbyme.angelhack.service.dto.post.PostDetailResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDetailResponseDto {

    private Long userId;
    private String email;
    private String nickName;
    private String pictureUrl;
    private List<PersonalMissionDetailDto> personalMissions;
    private List<PostDetailResponseDto> posts;

    public UserDetailResponseDto(User user) {
        this.userId = user.getId();
        this.email = user.getEmail();
        this.nickName = user.getNickname();
        this.personalMissions = user.getPersonalMissionList().stream()
                .map(PersonalMissionDetailDto::new)
                .collect(Collectors.toList());
        this.posts = user.getPostList().stream()
                .map(PostDetailResponseDto::new)
                .collect(Collectors.toList());
        this.pictureUrl = user.getPhoto();
    }
}
