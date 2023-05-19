package gcu.mp.domain.orderPost.domain;

import gcu.mp.domain.entity.BaseEntity;
import gcu.mp.domain.member.domin.Member;
import gcu.mp.domain.orderPost.vo.Progress;
import gcu.mp.domain.orderPost.vo.State;
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
public class OrderPost extends BaseEntity {
    String title;
    String content;
    String estimated_time;
    @Enumerated(EnumType.STRING)
    State state;
    @Enumerated(EnumType.STRING)
    Progress progress;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    Member member;

    public void setMember(Member member) {
        this.member = member;
        member.addOrderPost(this);
    }
}
