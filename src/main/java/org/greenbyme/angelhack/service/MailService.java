package org.greenbyme.angelhack.service;

import lombok.extern.slf4j.Slf4j;
import org.greenbyme.angelhack.domain.user.User;
import org.greenbyme.angelhack.service.dto.mail.MailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MailService {

    private static final String SIGN_UP_TITLE = "내가그린 가입을 환영합니다.";
    private static final String EMAIL_ADDRESS = "greenbyme.ko@gmail.com";

    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    public void sendSingUpMail(User user) {
        String contents = "안녕하세요 " + user.getName() + "님!\n 가입을 환영합니다";
        sendMail(new MailDto(user.getEmail(), SIGN_UP_TITLE, contents));
    }

    public void sendMail(MailDto dto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(dto.getDestinationAddress());
        message.setFrom(EMAIL_ADDRESS);
        message.setSubject(dto.getMailTitle());
        message.setText(dto.getMailContents());
        javaMailSender.send(message);
    }
}
