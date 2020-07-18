package org.greenbyme.angelhack.service.dto.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.user.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSaveRequestDto {

    private String name;
    private String email;
    private String password;
    private String nickname;
    private String photo;

    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .password(password)
                .nickname(nickname)
                .photo(photo)
                .build();
    }
}
