package com.techcareer.challenge.utilities.mail;


import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class MailServiceImpl implements MailService{

    private final JavaMailSender mailSender;

    @Override
    @Async
    public void sendEmail(String to, String email) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setText(email);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject("Confirm email");
        simpleMailMessage.setFrom("hello.user@gmail.com");
        mailSender.send(simpleMailMessage);
    }
}
