package gcu.mp.domain.orderPost.domain;

import gcu.mp.domain.deliveryPost.domain.DeliveryPostProgress;
import gcu.mp.domain.entity.BaseEntity;
import gcu.mp.domain.member.domin.Member;
import gcu.mp.domain.orderPost.vo.Progress;
import gcu.mp.domain.orderPost.vo.State;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

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
    @Builder.Default
    @OneToMany(mappedBy = "orderPost", cascade = CascadeType.ALL)
    List<OrderPostComment> orderPostCommentList = new ArrayList<>();
    @Builder.Default
    @OneToMany(mappedBy = "orderPost", cascade = CascadeType.ALL)
    List<OrderPostProgress> orderPostProgressList = new ArrayList<>();
    public void setMember(Member member) {
        this.member = member;
        member.addOrderPost(this);
    }

    public void addOrderPostComment(OrderPostComment orderPostComment) {
        this.orderPostCommentList.add(orderPostComment);
    }

    public void addOrderPostProgress(OrderPostProgress orderPostProgress) {
        this.orderPostProgressList.add(orderPostProgress);
    }

    public void updateProgress(Progress progress) {
        this.progress = progress;
    }
}
