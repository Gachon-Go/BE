package com.example.domain.mapper;

import com.example.core.dto.CheckMailCommand;
import com.example.core.valueobject.Email;
import com.example.core.valueobject.EmailCheck;
import org.springframework.stereotype.Component;

@Component
public class CheckEmailDataMapper {
    public EmailCheck emailCheck;

    public EmailCheck checkMailCommandToCheckEmail(CheckMailCommand checkMailCommand) {
        return EmailCheck.builder()
                .email(checkMailCommand.getEmail())
                .code(checkMailCommand.getMailCode())
                .build();
    }
}
