package gcu.project.gachongo.domain.member.domin;


import gcu.project.gachongo.domain.member.vo.Role;
import gcu.project.gachongo.domain.member.vo.Status;
import gcu.project.gachongo.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = PROTECTED)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Member extends BaseEntity {

    private String fcm_id;

    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private Status status;
    @OneToOne(mappedBy = "member")
    private SocialLogin socialLogin;

    @OneToOne(mappedBy = "member")
    private Profile profile;
}
