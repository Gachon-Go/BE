package gcu.project.gachongo.domain.member.domin;

import gcu.project.gachongo.global.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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

    private String authPlatform;

    private String authId;

}
