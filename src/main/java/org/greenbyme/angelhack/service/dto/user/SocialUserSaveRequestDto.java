package org.greenbyme.angelhack.service.dto.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.user.PlatformType;
import org.greenbyme.angelhack.domain.user.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SocialUserSaveRequestDto {

    @NotEmpty
    private String name;
    @NotEmpty @Email
    private String email;
    @NotEmpty
    private String nickname;
    @NotEmpty
    private String platformId;
    @NotNull
    private PlatformType platformType;

    public SocialUserSaveRequestDto(@NotEmpty String name, @NotEmpty @Email String email, @NotEmpty String nickname, @NotEmpty String platformId, @NotNull PlatformType platformType) {
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.platformId = platformId;
        this.platformType = platformType;
    }

    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .nickname(nickname)
                .platformId(platformId)
                .platformType(platformType)
                .build();
    }
}
