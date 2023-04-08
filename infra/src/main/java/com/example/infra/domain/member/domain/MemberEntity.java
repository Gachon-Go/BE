package com.example.infra.domain.member.domain;
import com.example.infra.domain.authorization.domain.SocialLoginEntity;
import com.example.core.model.Role;
import com.example.infra.domain.global.entity.BaseEntity;
import com.example.core.model.Status;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = PROTECTED)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name="member")
public class MemberEntity extends BaseEntity {

    private String fcm_id;

    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private Status status;
    @OneToOne(mappedBy = "member")
    private SocialLoginEntity socialLogin;

    @OneToOne(mappedBy = "member")
    private ProfileEntity profile;
}
