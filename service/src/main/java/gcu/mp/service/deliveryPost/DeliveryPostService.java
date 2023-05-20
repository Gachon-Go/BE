package gcu.mp.service.deliveryPost;

import gcu.mp.domain.member.domin.Member;

import java.util.List;

public interface DeliveryPostService {

    List<Member> getDeliveryPostProgressMemberIdByMemberId(Long memberId);

    Long getDeliveryPostProgressOrderIdByMemberId(Long memberId);
}
