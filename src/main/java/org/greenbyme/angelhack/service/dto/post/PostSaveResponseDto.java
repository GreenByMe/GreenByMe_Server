package org.greenbyme.angelhack.service.dto.post;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostSaveResponseDto {

    private Long postId;
    private String message;

    public PostSaveResponseDto(Long postId, double expectTree, int finishCount) {
        this.postId = postId;
        this.message = "축하합니다 <br> 인증 완료로 \"<p style=\\\"color:#26B679;\\\">" + expectTree/(double)finishCount
                + "개의 나무를 </p> <br> 심으셨습니다!";
    }
}
