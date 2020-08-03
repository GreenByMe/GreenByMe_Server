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
    private String pictureUrl;
    private String text;
    private Boolean open;

}
