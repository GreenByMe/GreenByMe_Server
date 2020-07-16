package org.greenbyme.angelhack.service.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenResponse {

    private String accessToken;
    private Long id;

    @Builder
    public TokenResponse(String accessToken, Long id) {
        this.accessToken = accessToken;
        this.id = id;
    }
}
