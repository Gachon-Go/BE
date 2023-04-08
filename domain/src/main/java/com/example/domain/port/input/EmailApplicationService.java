package com.example.domain.port.input;

import com.example.core.dto.CheckMailCommand;
import com.example.core.dto.EmailCommand;

public interface EmailApplicationService {
    void sendEmail(EmailCommand emailCommand);

    boolean verifyEmailCode(CheckMailCommand checkMailCommand);
}
