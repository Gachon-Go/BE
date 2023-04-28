package gcu.mp.domain.member.domin;

import gcu.mp.domain.entity.BaseEntity;
import gcu.mp.domain.member.vo.Role;
import gcu.mp.domain.member.vo.Status;
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
    @OneToOne(mappedBy = "member",cascade = CascadeType.ALL)
    private SocialLogin socialLogin;

    @OneToOne(mappedBy = "member",cascade = CascadeType.ALL)
    private Profile profile;

    public void setProfile(Profile profile){
        this.profile = profile;
        profile.setMember(this);
    }
    public void setSocialLogin(SocialLogin socialLogin){
        this.socialLogin = socialLogin;
        socialLogin.setMember(this);
    }
    public void resignMember(){
        this.status = Status.D;
    }
}
