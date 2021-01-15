package org.greenbyme.angelhack.service.dto.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PasswordValidRequestDto {

    private String password;

    public PasswordValidRequestDto(String password) {
        this.password = password;
    }
}
