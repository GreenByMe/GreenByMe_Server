package org.greenbyme.angelhack.service.dto.post;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.post.Post;

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

    public PostResponseDto(Post post) {
        if (post == null) {
            return;
        }
        this.postId = post.getId();
        this.nickName = post.getUser().getNickname();
        this.picture = post.getPicture();
        this.thumbsUp = post.getPostLikes().size();
    }

    public int compareTo(PostResponseDto o) { // 나이를 기준으로 오름차순
        return Long.compare(this.getPostId(), o.getPostId());
    }
}
