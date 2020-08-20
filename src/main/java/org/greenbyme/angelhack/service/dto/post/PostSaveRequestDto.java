package org.greenbyme.angelhack.service.dto.post;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostSaveRequestDto {

    private Long personalMission_id;
    private String title;
    private String text;
    private Boolean open;

    public PostSaveRequestDto(Long personalMission_id, String title, String text, Boolean open) {
        this.personalMission_id = personalMission_id;
        this.title = title;
        this.text = text;
        this.open = open;
    }
}
