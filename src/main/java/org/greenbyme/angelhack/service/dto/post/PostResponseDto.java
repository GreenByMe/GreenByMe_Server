package org.greenbyme.angelhack.service.dto.post;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public int compareTo(PostResponseDto o) { // 나이를 기준으로 오름차순
        return Long.compare(this.getPostId(), o.getPostId());
    }

}
