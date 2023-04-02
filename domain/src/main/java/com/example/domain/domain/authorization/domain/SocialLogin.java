package com.example.domain.domain.authorization.domain;


import com.example.domain.domain.member.domain.Member;
import com.example.domain.global.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = PROTECTED)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SocialLogin extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String authPlatform;

    private String authId;

    private String refreshToken;


}
