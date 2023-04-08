package com.example.domain;

import com.example.core.dto.CreateMemberCommand;
import com.example.domain.core.Member;
import com.example.domain.mapper.MemberDataMapper;
import com.example.domain.port.output.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class MemberCreateHelper {
    private final MemberDataMapper memberDataMapper;
    private final MemberRepository memberRepository;

    public Member persistMember(CreateMemberCommand createMemberCommand) {
        Member member = memberDataMapper.createMemberCommandToMember(createMemberCommand);
        return save(member);
    }

    private Member save(Member member) {
        return memberRepository.save(member);
    }
}
