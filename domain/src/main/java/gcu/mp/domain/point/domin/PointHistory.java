package gcu.mp.domain.point.domin;

import gcu.mp.domain.entity.BaseEntity;
import gcu.mp.domain.member.domin.Member;
import gcu.mp.domain.point.vo.State;
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
public class PointHistory extends BaseEntity{
    long point;
    String flag;
    @Enumerated(EnumType.STRING)
    private State state;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    Member member;
}
