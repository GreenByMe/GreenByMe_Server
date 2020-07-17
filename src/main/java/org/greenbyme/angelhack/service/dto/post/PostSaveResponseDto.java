package org.greenbyme.angelhack.service.dto.post;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostSaveResponseDto {

    private Long postId;
    private String message;

    public PostSaveResponseDto(Long postId) {
        this.postId = postId;
        this.message = "success";
    }
}
