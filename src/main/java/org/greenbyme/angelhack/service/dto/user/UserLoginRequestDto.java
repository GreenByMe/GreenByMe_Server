package org.greenbyme.angelhack.service.dto.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserLoginRequestDto {

    @NotEmpty @Email
    private String email;
    @NotEmpty
    private String password;
}
