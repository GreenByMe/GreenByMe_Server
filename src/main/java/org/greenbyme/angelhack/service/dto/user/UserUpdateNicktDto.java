package org.greenbyme.angelhack.service.dto.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUpdateNicktDto {

    @NotNull
    private String nickName;

    public UserUpdateNicktDto(String nickName) {
        this.nickName = nickName;
    }
}
