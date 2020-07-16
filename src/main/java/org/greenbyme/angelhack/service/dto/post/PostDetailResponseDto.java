package org.greenbyme.angelhack.service.dto.post;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.post.Post;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDetailResponseDto {

    private Long postId;
    private String nickName;
    private String picture;
    private Integer thumbsUp;
    private String text;

    public PostDetailResponseDto(Post post) {
        this.postId = post.getId();
        this.nickName = post.getUser().getNickname();
        this.picture = post.getPicture();
        this.thumbsUp = post.getThumbsUp();
        this.text = post.getText();
    }
}