package gcu.mp.domain.member.domin;

import gcu.mp.domain.entity.BaseEntity;
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
public class Profile extends BaseEntity {
    private String nickname;

    private String email;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;
    public Profile createProfile(String email, String nickname){
        return Profile.builder()
                .email(email)
                .nickname(nickname)
                .build();
    }
    public void setMember(Member member){
        this.member = member;
    }
    public void updateNickname(String nickname){
        this.nickname = nickname;
    }
}
