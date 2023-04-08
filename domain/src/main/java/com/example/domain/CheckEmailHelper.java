package com.example.domain;

import com.example.core.dto.CheckMailCommand;
import com.example.core.valueobject.EmailCheck;
import com.example.domain.mapper.CheckEmailDataMapper;
import com.example.domain.port.output.publisher.EmailPublisher;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class CheckEmailHelper {
    private final EmailPublisher emailPublisher;
    private final CheckEmailDataMapper checkEmailDataMapper;
    public boolean persist(CheckMailCommand checkMailCommand) {
        EmailCheck checkMail = checkEmailDataMapper.checkMailCommandToCheckEmail(checkMailCommand);
        return emailPublisher.CheckEmail(checkMail);
    }
}
