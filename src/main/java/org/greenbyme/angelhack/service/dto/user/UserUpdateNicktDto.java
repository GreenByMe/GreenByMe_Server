package org.greenbyme.angelhack.service.dto.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUpdateNicktDto {
    
    private String nickName;

    public UserUpdateNicktDto(String nickName) {
        this.nickName = nickName;
    }
}
