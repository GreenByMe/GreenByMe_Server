package org.greenbyme.angelhack.service.dto.post;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.post.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDetailResponseDto {

    private Long postId;
    private String nickName;
    private String picture;
    private Integer thumbsUp;
    private String title;
    private String text;
    private Boolean isMine;
    private LocalDateTime createDate;
    private LocalDateTime lastModifiedDate;
    private List<String> tags;

    public PostDetailResponseDto(Post post, boolean mine) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.nickName = post.getUser().getNickname();
        this.picture = post.getPicture();
        this.thumbsUp = post.getPostLikes().size();
        this.text = post.getText();
        this.isMine = mine;
        this.createDate = post.getCreatedDate();
        this.lastModifiedDate = post.getLastModifiedDate();
        this.tags = post.getPostTagList().stream()
                .map(p -> p.getTag().getTagName())
                .collect(Collectors.toList());
    }
}
