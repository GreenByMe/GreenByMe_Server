package org.greenbyme.angelhack.service.dto.post;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostByTagResponseDto {

    private String tagName;
    private List<PostResponseDto> posts;

    public PostByTagResponseDto(String tagName, List<PostResponseDto> posts) {
        this.tagName = tagName;
        this.posts = posts;
    }
}
