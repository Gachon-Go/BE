package gcu.mp.domain.deliveryPost.repository;

import gcu.mp.domain.deliveryPost.domain.DeliveryPost;
import gcu.mp.domain.deliveryPost.vo.Progress;
import gcu.mp.domain.deliveryPost.vo.State;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface DeliveryPostRepository extends JpaRepository<DeliveryPost, Long> {

    Optional<DeliveryPost> findByIdAndState(Long postId, State state);

    List<DeliveryPost> findByState(State state, PageRequest pageRequest);

    boolean existsByMemberIdAndStateAndProgress(Long memberId, State state, Progress progress);
}
