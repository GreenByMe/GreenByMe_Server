package org.greenbyme.angelhack.service.dto.post;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
