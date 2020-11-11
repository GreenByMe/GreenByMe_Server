package org.greenbyme.angelhack.service.dto.post;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostSaveResponseDto {

    private Long postId;
    private String message;

    public PostSaveResponseDto(Long postId, String nickName, double expectTree, int finishCount) {
        this.postId = postId;
        this.message = "축하합니다. <br>" + nickName + "님<br><b><font color=\"#26B679\">" + Math.round(expectTree / (double) finishCount) * 100.0 + "개의 나무</font></b><br>를심으셨습니다!";
    }
}
