package com.example.infra.domain.member.mapper;


import com.example.domain.core.Member;
import com.example.infra.domain.member.domain.MemberEntity;
import org.springframework.stereotype.Component;

@Component
public class MemberDataAccessMapper {

    public MemberEntity memberToMemberEntity(Member member) {
        MemberEntity memberEntity = MemberEntity.builder()
                .fcm_id(member.getFcm_id())
                .role(member.getRole())
                .status(member.getStatus())
                .createdAt(member.getCreatedAt())
                .updatedAt(member.getUpdatedAt())
                .id(member.getId())
                .build();
        return memberEntity;
    }

    public Member memberEntityToMember(MemberEntity memberEntity) {
        return Member.builder()
                .id(memberEntity.getId())
                .fcm_id(memberEntity.getFcm_id())
                .build();
    }
}
