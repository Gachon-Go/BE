package com.example.domain.domain.member.domain;
import com.example.domain.domain.authorization.domain.SocialLogin;
import com.example.domain.domain.model.Role;
import com.example.domain.global.BaseEntity;
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
public class Member extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "member")
    private SocialLogin socialLogin;

    @OneToOne(mappedBy = "member")
    private Profile profile;
}
