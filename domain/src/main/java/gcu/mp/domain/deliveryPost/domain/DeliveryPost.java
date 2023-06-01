package gcu.mp.domain.deliveryPost.domain;

import gcu.mp.domain.deliveryPost.vo.Progress;
import gcu.mp.domain.deliveryPost.vo.State;
import gcu.mp.domain.entity.BaseEntity;
import gcu.mp.domain.member.domin.Member;
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
    @Builder.Default
    @OneToMany(mappedBy = "deliveryPost", cascade = CascadeType.ALL)
    List<DeliveryPostComment> deliveryPostCommentList = new ArrayList<>();
    @Builder.Default
    @OneToMany(mappedBy = "deliveryPost", cascade = CascadeType.ALL)
    List<DeliveryPostProgress> deliveryPostProgressList = new ArrayList<>();

    public void setMember(Member member) {
        this.member = member;
        member.addDeliveryPost(this);
    }

    public void addDeliveryPostComment(DeliveryPostComment deliveryPostComment) {
        this.deliveryPostCommentList.add(deliveryPostComment);
    }

    public void addDeliveryPostProgress(DeliveryPostProgress deliveryPostProgress) {
        this.deliveryPostProgressList.add(deliveryPostProgress);
    }

    public void updateProgress(Progress progress) {
        this.progress = progress;
    }
}
