package gcu.mp.mailclient;

import gcu.mp.redis.Email;
import gcu.mp.redis.RedisUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;


import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {
    private final SpringTemplateEngine templateEngine;
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
            message.setSubject("[GachonGo] 이메일 인증번호를 안내해드립니다.");
            message.setFrom(configEmail);
            message.setText(setContext(email, authCode), "utf-8", "html");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        redisUtil.setDataExpire(email, authCode, 60 * 30L);

        return message;
    }

    private String setContext(String email, String authCode) { // 타임리프 설정하는 코드
        Context context = new Context();
        context.setVariable("email", email); // Template에 전달할 데이터 설정
        context.setVariable("authCode", authCode); // Template에 전달할 데이터 설정
        return templateEngine.process("mail", context); // mail.html
    }

    // 메일 보내기 ( 비동기 처리)
    @Async
    public void sendEmail(String toEmail) {
        if (redisUtil.existData(toEmail)) {
            redisUtil.deleteData(toEmail);
        }
        MimeMessage emailForm;
        emailForm = createEmailForm(toEmail);
        mailSender.send(emailForm);
    }

    // 코드 검증
    public Boolean verifyEmailCode(Email emailCheck) {
        String codeFoundByEmail = redisUtil.getData(emailCheck.getEmail());
        if (codeFoundByEmail == null) {
            return false;
        }
        return codeFoundByEmail.equals(emailCheck.getCode());
    }
}