package gcu.mp.domain.orderPost.domain;

import gcu.mp.domain.entity.BaseEntity;
import gcu.mp.domain.member.domin.Member;
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
public class OrderPostComment extends BaseEntity {
    String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_post_id")
    OrderPost orderPost;
    @Enumerated(EnumType.STRING)
    State state;
}
