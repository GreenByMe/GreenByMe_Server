package org.greenbyme.angelhack.service.dto.post;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostUpdateRequestDto {

    private Long userId;
    private String title;
    private String text;
    private Boolean open;
}
