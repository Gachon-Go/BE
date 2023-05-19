package gcu.mp.domain.deliveryPost.domain;

import gcu.mp.domain.deliveryPost.vo.Progress;
import gcu.mp.domain.deliveryPost.vo.State;
import gcu.mp.domain.entity.BaseEntity;
import gcu.mp.domain.member.domin.Member;
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
public class DeliveryPost extends BaseEntity {
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
}
