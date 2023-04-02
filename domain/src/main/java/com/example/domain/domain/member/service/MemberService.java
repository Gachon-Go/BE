package com.example.domain.domain.member.service;

import com.example.domain.domain.member.domain.Member;
import com.example.domain.domain.member.repository.MemberRepository;
import com.example.domain.domain.model.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public void createUser() {
        Member member = Member.builder()
                .role(Role.USER)
                .build();
        memberRepository.save(member);
    }

    public List<Member> findUser() {
        return memberRepository.findAll();
    }
}
