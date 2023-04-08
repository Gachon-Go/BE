package com.example.domain;

import com.example.core.dto.CheckMailCommand;
import com.example.core.dto.EmailCommand;
import com.example.domain.port.input.EmailApplicationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
@AllArgsConstructor
public class EmailApplicationServiceImp implements EmailApplicationService {
    private final EmailCommandHandler emailCommandHandler;
    private final CheckEmailCommandHandler checkEmailCommandHandler;
    @Override
    public void sendEmail(EmailCommand emailCommand) {
        emailCommandHandler.sendEmail(emailCommand);
    }

    @Override
    public boolean verifyEmailCode(CheckMailCommand checkMailCommand) {
        return checkEmailCommandHandler.verifyEmailCode(checkMailCommand);
    }
}
