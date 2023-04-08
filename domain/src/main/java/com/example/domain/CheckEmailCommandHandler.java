package com.example.domain;

import com.example.core.dto.CheckMailCommand;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
@AllArgsConstructor
public class CheckEmailCommandHandler {
    private final CheckEmailHelper checkEmailHelper;
    public boolean verifyEmailCode(CheckMailCommand checkMailCommand) {
        return checkEmailHelper.persist(checkMailCommand);
    }
}
