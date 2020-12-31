package org.greenbyme.angelhack.service;

import lombok.extern.slf4j.Slf4j;
import org.greenbyme.angelhack.domain.user.User;
import org.greenbyme.angelhack.service.dto.mail.MailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@Service
public class MailService {

    private static final String SIGN_UP_TITLE = "[공지] 내가그린 가입을 환영합니다.";
    private static final String EMAIL_ADDRESS = "greenbyme.ko@gmail.com";

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${mail.sender.ip}")
    private String ip;

    public String setContent(String name, String key) {
        String url = "https://" + ip + "/api/mail/certificate/" + key;
        String content = "<h2>안녕하세요 " + name + "님</h2><br>" +
                "<h2>내가 그린에 가입 하신 것을 환영합니다.</h2>" +
                "<h3>인증을 위해 아래 버튼을 클릭해주세요.<br><br>" +
                "<a href=\"" + url + "\">인증하기 </a>";
        return content;
    }

    @Async
    public void sendSingUpMail(User user, String key) throws MessagingException {
        String contents = setContent(user.getName(), key);
        sendMail(new MailDto(user.getEmail(), SIGN_UP_TITLE, contents));
    }

    @Async
    public void sendMail(MailDto dto) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "utf-8");
        mimeMessageHelper.setFrom(EMAIL_ADDRESS);
        mimeMessageHelper.setTo(dto.getDestinationAddress());
        mimeMessageHelper.setSubject(dto.getMailTitle());
        mimeMessageHelper.setText(dto.getMailContents(), true);
        javaMailSender.send(mimeMessage);
    }
}
