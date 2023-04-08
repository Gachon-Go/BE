package com.example.infra.domain.member.adapter;


import com.example.domain.core.Member;
import com.example.domain.port.output.repository.MemberRepository;
import com.example.infra.domain.member.mapper.MemberDataAccessMapper;
import com.example.infra.domain.member.repository.MemberJpaRepository;
import com.example.infra.domain.member.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MemberRepositoryImp implements MemberRepository {
    private final MemberDataAccessMapper memberDataAccessMapper;
    private final MemberJpaRepository memberJpaRepository;
    private final MemberService memberService;
    @Override
    public Member save(Member member) {
        return memberDataAccessMapper.memberEntityToMember(
                memberService.createUser(memberDataAccessMapper.memberToMemberEntity(member)
        ));
    }
}
