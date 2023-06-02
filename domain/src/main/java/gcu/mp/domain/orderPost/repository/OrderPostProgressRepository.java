package gcu.mp.domain.orderPost.repository;

import gcu.mp.domain.orderPost.domain.OrderPostProgress;
import gcu.mp.domain.orderPost.vo.ProgressState;
import gcu.mp.domain.orderPost.vo.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderPostProgressRepository extends JpaRepository<OrderPostProgress,Long> {
    Optional<OrderPostProgress> findByMemberIdAndStateAndProgressState(Long memberId, State state, ProgressState progressState);

    List<OrderPostProgress> findByOrderPostIdAndStateAndProgressState(Long orderPostId, State state, ProgressState progressState);

    List<OrderPostProgress> findByOrderPostIdAndState(Long orderPostId, State state);

    boolean existsByOrderPostIdAndMemberIdAndState(Long orderPostId, Long memberId, State state);

    List<OrderPostProgress> findByMemberIdAndState(Long id, State state);

    List<OrderPostProgress> findAllByMemberIdAndStateAndProgressState(Long memberId, State state, ProgressState progressState);
}
