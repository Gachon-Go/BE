package com.example.domain;

import com.example.core.dto.CreateMemberCommand;
import com.example.core.dto.CreateMemberResponse;
import com.example.domain.port.input.MemberApplicationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
@AllArgsConstructor
public class MemberApplicationServiceImp implements MemberApplicationService {
    private final MemberCreateCommandHandler memberCreateCommandHandler;
    @Override
    public CreateMemberResponse createMember(CreateMemberCommand createMemberCommand) {
        return memberCreateCommandHandler.createMember(createMemberCommand);
    }
}
