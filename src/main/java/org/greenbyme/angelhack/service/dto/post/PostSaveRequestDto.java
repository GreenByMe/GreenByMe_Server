package org.greenbyme.angelhack.service.dto.post;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.greenbyme.angelhack.domain.baseEntity.BaseEntity;
import org.greenbyme.angelhack.domain.missionInfo.MissionInfo;
import org.greenbyme.angelhack.domain.post.Post;
import org.greenbyme.angelhack.domain.user.User;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostSaveRequestDto {

    private Long userId;
    private Long missionInfoId;
    private String title;
    private String text;
    private Boolean open;

    public PostSaveRequestDto( Long userId, Long missionInfoId, String title,  String text, Boolean open) {
        this.userId = userId;
        this.missionInfoId = missionInfoId;
        this.title = title;
        this.text = text;
        this.open = open;
    }
}
