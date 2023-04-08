package com.example.infra.domain.authorization.domain;


import com.example.infra.domain.member.domain.MemberEntity;
import com.example.infra.domain.global.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = PROTECTED)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "social_login")
public class SocialLoginEntity extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    private String authPlatform;

    private String authId;

}
