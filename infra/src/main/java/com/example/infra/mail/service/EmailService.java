package com.example.infra.mail.service;

import com.example.core.valueobject.EmailCheck;
import com.example.infra.system.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final RedisUtil redisUtil;

    @Value("${spring.mail.username}")
    private String configEmail;

    private String createdCode() {
        int leftLimit = 48; // number '0'
        int rightLimit = 122; // alphabet 'z'
        int targetStringLength = 6;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    // 메일 반환

    private MimeMessage createEmailForm(String email) {

        String authCode = createdCode();

        MimeMessage message = mailSender.createMimeMessage();
        try {
            message.addRecipients(MimeMessage.RecipientType.TO, email);
            message.setSubject("안녕하세요 인증번호입니다.");
            message.setFrom(configEmail);
            message.setText(authCode, "utf-8", "html");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }


        redisUtil.setDataExpire(email, authCode, 60 * 30L);

        return message;
    }


    // 메일 보내기
    public void sendEmail(String toEmail) {
        if (redisUtil.existData(toEmail)) {
            redisUtil.deleteData(toEmail);
        }
        MimeMessage emailForm = null;
        emailForm = createEmailForm(toEmail);

        mailSender.send(emailForm);

    }

    // 코드 검증
    public Boolean verifyEmailCode(EmailCheck emailCheck) {
        String codeFoundByEmail = redisUtil.getData(emailCheck.getEmail());
        if (codeFoundByEmail == null) {
            return false;
        }
        return codeFoundByEmail.equals(emailCheck.getCode());
    }
}