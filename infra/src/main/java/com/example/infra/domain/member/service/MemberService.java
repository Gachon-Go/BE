package com.example.infra.domain.member.service;

import com.example.core.model.Status;
import com.example.domain.core.Member;
import com.example.infra.domain.member.domain.MemberEntity;
import com.example.infra.domain.member.mapper.MemberDataAccessMapper;
import com.example.infra.domain.member.repository.MemberJpaRepository;
import com.example.core.model.Role;
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
    private final MemberDataAccessMapper memberDataAccessMapper;
    private final MemberJpaRepository memberJpaRepository;


    public MemberEntity createUser(MemberEntity memberEntity) {
       return memberJpaRepository.save(memberEntity);
    }
}
