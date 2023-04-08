package com.example.domain.mapper;

import com.example.core.dto.EmailCommand;
import com.example.core.valueobject.Email;
import org.springframework.stereotype.Component;

@Component
public class EmailDataMapper {

    public Email emailCommandToEmail(EmailCommand emailCommand) {
        return Email.builder()
                .email(emailCommand.getEmail())
                .build();
    }
}
