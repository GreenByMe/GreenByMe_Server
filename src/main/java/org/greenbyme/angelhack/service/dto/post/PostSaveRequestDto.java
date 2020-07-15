package org.greenbyme.angelhack.service.dto.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.greenbyme.angelhack.domain.missionInfo.MissionInfo;
import org.greenbyme.angelhack.domain.post.Post;
import org.greenbyme.angelhack.domain.user.User;

@Slf4j
@Getter
@NoArgsConstructor
public class PostSaveRequestDto {

    private User user;
    private MissionInfo missionInfo;
    private String title;
    private String text;
    private String picture;
    private Boolean open;

    public Post toEntity() {
        return Post.builder()
                .user(user)
                .missionInfo(missionInfo)
                .title(title)
                .text(text)
                .picture(picture)
                .open(open)
                .build();
    }
}
