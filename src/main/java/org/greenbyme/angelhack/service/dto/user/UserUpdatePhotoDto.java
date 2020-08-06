package org.greenbyme.angelhack.service.dto.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUpdatePhotoDto {

    private Long userId;

    public UserUpdatePhotoDto(Long userId) {
        this.userId = userId;
    }
}
