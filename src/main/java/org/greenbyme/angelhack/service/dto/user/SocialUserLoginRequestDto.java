package org.greenbyme.angelhack.service.dto.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SocialUserLoginRequestDto {

    @NotEmpty
    private String platformId;

    public SocialUserLoginRequestDto(@NotEmpty String platformId) {
        this.platformId = platformId;
    }
}
