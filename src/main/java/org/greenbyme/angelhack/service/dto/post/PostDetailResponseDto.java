package org.greenbyme.angelhack.service.dto.post;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.post.Post;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDetailResponseDto {

    private Long postId;
    private String nickName;
    private String picture;
    private Integer thumbsUp;
    private String title;
    private String text;
    private LocalDateTime createDate;
    private LocalDateTime lastModifiedDate;

    public PostDetailResponseDto(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.nickName = post.getUser().getNickname();
        this.picture = post.getPicture();
        this.thumbsUp = post.getThumbsUp();
        this.text = post.getText();
        this.createDate = post.getCreatedDate();
        this.lastModifiedDate = post.getLastModifiedDate();
    }
}
