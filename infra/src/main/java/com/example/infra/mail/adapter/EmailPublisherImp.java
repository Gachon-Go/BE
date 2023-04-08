package com.example.infra.mail.adapter;

import com.example.core.valueobject.Email;
import com.example.core.valueobject.EmailCheck;
import com.example.domain.port.output.publisher.EmailPublisher;
import com.example.infra.mail.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;

@Component
@AllArgsConstructor
public class EmailPublisherImp implements EmailPublisher {
    private final EmailService emailService;
    @Override
    public void sendMail(Email email) {
        emailService.sendEmail(email.getEmail());
    }

    @Override
    public boolean CheckEmail(EmailCheck checkMail) {
        return emailService.verifyEmailCode(checkMail);
    }
}
