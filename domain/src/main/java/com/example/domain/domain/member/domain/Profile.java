package com.example.domain.domain.member.domain;

import com.example.domain.global.BaseEntity;
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
public class Profile extends BaseEntity {
    private String name;
    private String studentNumber;
    private String nickname;
    private String email;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
