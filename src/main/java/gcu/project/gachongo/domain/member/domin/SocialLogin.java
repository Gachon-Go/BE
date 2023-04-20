package gcu.project.gachongo.domain.member.domin;

import gcu.project.gachongo.global.common.entity.BaseEntity;
import gcu.project.gachongo.service.oauth.dto.core.OAuthType;
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
public class SocialLogin extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @Enumerated(EnumType.STRING)
    private OAuthType provider;

    private String authId;

    public void setMember(Member member) {
        this.member = member;
    }
}
