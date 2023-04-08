package com.example.infra.domain.member.domain;

import com.example.infra.domain.global.entity.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = PROTECTED)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "profile")
public class ProfileEntity extends BaseEntity {
    private String nickname;

    private String email;

    @OneToOne
    @JoinColumn(name = "member_id")
    private MemberEntity member;
}
