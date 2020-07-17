package org.greenbyme.angelhack.service.dto.post;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostSaveResponseDto {

    private Long missionId;
    private String message;

    public PostSaveResponseDto(Long missionId) {
        this.missionId = missionId;
        this.message = "success";
    }
}
