package org.greenbyme.angelhack.service.dto.post;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.post.Post;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostUpdateResponseDto {

    private Long id;

    public PostUpdateResponseDto(Post post){
        this.id = post.getId();
    }
}
