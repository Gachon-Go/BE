package com.example.domain.mapper;

import com.example.core.dto.CreateMemberCommand;
import com.example.core.dto.CreateMemberResponse;
import com.example.core.model.Role;
import com.example.domain.core.Member;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
@Component
public class MemberDataMapper {

//member가 null
    public CreateMemberResponse memberToCreateMemberResponse(Member member) {
        return CreateMemberResponse.builder()
                .id(member.getId())
                .build();
    }

    public Member createMemberCommandToMember(CreateMemberCommand createMemberCommand) {
        return  Member.builder()
                .fcm_id(createMemberCommand.getFcmId())
                .build();
    }
}
