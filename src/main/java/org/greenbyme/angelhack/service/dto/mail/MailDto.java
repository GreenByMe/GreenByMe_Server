package org.greenbyme.angelhack.service.dto.mail;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MailDto {

    private String destinationAddress;
    private String mailTitle;
    private String mailContents;

    public MailDto(String destinationAddress, String mailTitle, String mailContents) {
        this.destinationAddress = destinationAddress;
        this.mailTitle = mailTitle;
        this.mailContents = mailContents;
    }
}
