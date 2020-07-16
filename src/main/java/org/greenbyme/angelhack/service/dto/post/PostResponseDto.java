package org.greenbyme.angelhack.service.dto.post;

import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PostResponseDto {

    private Long postId;
    private String nickName;
    private String picture;
    private Integer thumbsUp;

    @Builder
    public PostResponseDto(Long postId, String nickName, String picture, Integer thumbsUp) {
        this.postId = postId;
        this.nickName = nickName;
        this.picture = picture;
        this.thumbsUp = thumbsUp;
    }
}
