package gcu.mp.domain.deliveryPost.repository;

import gcu.mp.domain.deliveryPost.domain.DeliveryPostProgress;
import gcu.mp.domain.deliveryPost.vo.ProgressState;
import gcu.mp.domain.deliveryPost.vo.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryPostProgressRepository extends JpaRepository<DeliveryPostProgress,Long> {
    Optional<DeliveryPostProgress> findByMemberIdAndStateAndProgressState(Long memberId, State state, ProgressState progressState);

    List<DeliveryPostProgress> findByDeliveryPostIdAndStateAndProgressState(Long orderPostId, State state, ProgressState progressState);

    List<DeliveryPostProgress> findByDeliveryPostIdAndState(Long deliveryPostId, State state);
}
