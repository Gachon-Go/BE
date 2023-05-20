package gcu.mp.service.deliveryPost;

import gcu.mp.domain.deliveryPost.domain.DeliveryPostProgress;
import gcu.mp.domain.deliveryPost.repository.DeliveryPostProgressRepository;
import gcu.mp.domain.deliveryPost.repository.DeliveryPostRepository;
import gcu.mp.domain.deliveryPost.vo.ProgressState;
import gcu.mp.domain.deliveryPost.vo.State;
import gcu.mp.domain.entity.BaseEntity;
import gcu.mp.domain.member.domin.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class DeliveryPostServiceImpl implements DeliveryPostService {
    private final DeliveryPostRepository deliveryPostRepository;
    private final DeliveryPostProgressRepository deliveryPostProgressRepository;
    @Override
    public List<Member> getDeliveryPostProgressMemberIdByMemberId(Long memberId) {
        Optional<DeliveryPostProgress> deliveryPostProgressOptional = deliveryPostProgressRepository.findByMemberIdAndStateAndProgressState(memberId,State.A, ProgressState.ING);
        if(deliveryPostProgressOptional.isEmpty())
            return new ArrayList<>();
        else {
            Long orderPostId = deliveryPostProgressOptional.get().getId();
            List<DeliveryPostProgress> deliveryPostProgressList = deliveryPostProgressRepository.findByDeliveryPostIdAndStateAndProgressState(orderPostId,State.A,ProgressState.ING);
            return deliveryPostProgressList.stream().map(
                    DeliveryPostProgress::getMember
            ).collect(Collectors.toList());
        }
    }

    @Override
    public Long getDeliveryPostProgressOrderIdByMemberId(Long memberId) {
        Optional<DeliveryPostProgress> deliveryPostProgressOptional = deliveryPostProgressRepository.findByMemberIdAndStateAndProgressState(memberId,State.A, ProgressState.ING);
        return deliveryPostProgressOptional.map(BaseEntity::getId).orElse(null);
    }
}
