package org.greenbyme.angelhack.service.dto.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.missionInfo.MissionInfo;
import org.greenbyme.angelhack.domain.user.User;

@Getter
@NoArgsConstructor
public class PostUpdateRequestDto {

    private User user;
    private MissionInfo missionInfo;
    private String title;
    private String text;
    private String picture;
    private Boolean open;
}
