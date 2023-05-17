package gcu.mp.domain.member.domin;

import gcu.mp.domain.entity.BaseEntity;
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
    private String provider;
    private String authId;

    public void setMember(Member member) {
        this.member = member;
    }
}
