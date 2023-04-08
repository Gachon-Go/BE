package com.example.domain;

import com.example.core.common.error.ErrorCode;
import com.example.core.common.exception.CustomException;
import com.example.core.dto.EmailCommand;
import com.example.core.valueobject.Email;
import com.example.domain.core.Profile;
import com.example.domain.mapper.EmailDataMapper;
import com.example.domain.port.output.publisher.EmailPublisher;
import com.example.domain.port.output.repository.ProfileRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
public class SendEmailHelper {
    private final ProfileRepository profileRepository;
    private final EmailDataMapper emailDataMapper;
    private final EmailPublisher emailPublisher;
    public void persisEmail(EmailCommand emailCommand) {
        checkEmail(emailCommand.getEmail());
        Email email = emailDataMapper.emailCommandToEmail(emailCommand);
        sendMail(email);
    }

    private void sendMail(Email email) {
        emailPublisher.sendMail(email);
    }

    private void checkEmail(String email) {
        Optional<Profile> profile = profileRepository.findProfileByEmail(email);
        if(profile.isPresent()){
            throw new CustomException(ErrorCode.DUPLICATED_EMAIL);
        }
    }
}
