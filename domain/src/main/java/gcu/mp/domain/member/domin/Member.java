package gcu.mp.domain.member.domin;

import gcu.mp.domain.deliveryPost.domain.DeliveryPost;
import gcu.mp.domain.deliveryPost.domain.DeliveryPostComment;
import gcu.mp.domain.deliveryPost.domain.DeliveryPostProgress;
import gcu.mp.domain.entity.BaseEntity;
import gcu.mp.domain.member.vo.Role;
import gcu.mp.domain.member.vo.State;
import gcu.mp.domain.orderPost.domain.OrderPost;
import gcu.mp.domain.orderPost.domain.OrderPostComment;
import gcu.mp.domain.orderPost.domain.OrderPostProgress;
import gcu.mp.domain.pay.domain.PayHistory;
import gcu.mp.domain.point.domin.PointHistory;
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
public class Member extends BaseEntity {

    private String fcm_id;

    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private State state;
    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private SocialLogin socialLogin;
    private String nickname;
    private String email;
    private long point;
    private String image;
    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    List<PointHistory> pointHistoryList = new ArrayList<>();
    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    List<PayHistory> payHistoryList = new ArrayList<>();
    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    List<OrderPost> orderPostList = new ArrayList<>();
    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    List<OrderPostComment> orderPostCommentList = new ArrayList<>();
    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    List<DeliveryPost> deliveryPostList = new ArrayList<>();
    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    List<DeliveryPostComment> deliveryPostCommentList = new ArrayList<>();
    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    List<OrderPostProgress> orderPostProgressList = new ArrayList<>();
    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    List<DeliveryPostProgress> deliveryPostProgressList = new ArrayList<>();
    public void setSocialLogin(SocialLogin socialLogin) {
        this.socialLogin = socialLogin;
        socialLogin.setMember(this);
    }

    public void resignMember() {
        this.state = State.D;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void addPayHistory(PayHistory payHistory) {
        payHistoryList.add(payHistory);
    }

    public void addPoint(int totalAmount) {
        this.point += totalAmount;
    }

    public void addPointHistory(PointHistory pointHistory) {
        this.pointHistoryList.add(pointHistory);
    }

    public void setProfileImage(String s3ImageUrl) {
        this.image = s3ImageUrl;
    }

    public void addOrderPost(OrderPost orderPost) {
        this.orderPostList.add(orderPost);
    }

    public void addOrderPostComment(OrderPostComment orderPostComment) {
        this.orderPostCommentList.add(orderPostComment);
    }

    public void addOrderPostProgress(OrderPostProgress orderPostProgress) {
        this.orderPostProgressList.add(orderPostProgress);
    }

    public void addDeliveryPost(DeliveryPost deliveryPost) {
        this.deliveryPostList.add(deliveryPost);
    }

    public void addDeliveryPostComment(DeliveryPostComment deliveryPostComment) {
        this.deliveryPostCommentList.add(deliveryPostComment);
    }

    public void addDeliveryPostProgress(DeliveryPostProgress deliveryPostProgress) {
        this.deliveryPostProgressList.add(deliveryPostProgress);
    }
}
