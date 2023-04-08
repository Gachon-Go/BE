package com.example.domain;

import com.example.core.dto.CreateMemberCommand;
import com.example.core.dto.CreateMemberResponse;
import com.example.domain.core.Member;
import com.example.domain.mapper.MemberDataMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class MemberCreateCommandHandler {
    private final MemberCreateHelper memberCreateHelper;
    private final MemberDataMapper memberDataMapper;

    public CreateMemberResponse createMember(CreateMemberCommand createMemberCommand) {
        Member member = memberCreateHelper.persistMember(createMemberCommand);
        return memberDataMapper.memberToCreateMemberResponse(member);
    }
}
