package com.example.domain;

import com.example.core.dto.EmailCommand;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class EmailCommandHandler {
    private final SendEmailHelper sendEmailHelper;
    public void sendEmail(EmailCommand emailCommand) {
        sendEmailHelper.persisEmail(emailCommand);
    }
}