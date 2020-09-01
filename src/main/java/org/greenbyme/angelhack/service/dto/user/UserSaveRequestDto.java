package org.greenbyme.angelhack.service.dto.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.user.User;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSaveRequestDto {

    @NotEmpty
    private String name;
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
    @NotEmpty
    private String nickname;

    public UserSaveRequestDto(String name, String email, String password, String nickname) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .password(password)
                .nickname(nickname)
                .build();
    }
}
