package org.greenbyme.angelhack.service.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenResponse {

    private String token;

    @Builder
    public TokenResponse(String accessToken) {
        this.token = accessToken;
    }
}
