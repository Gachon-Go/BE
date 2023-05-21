package gcu.mp.domain.deliveryPost.repository;

import gcu.mp.domain.deliveryPost.domain.DeliveryPost;
import gcu.mp.domain.deliveryPost.vo.State;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface DeliveryPostRepository extends JpaRepository<DeliveryPost, Long> {

    Optional<DeliveryPost> findByIdAndState(Long postId, State state);

    Optional<DeliveryPost> findByMemberIdAndState(Long memberId, State state);

    List<DeliveryPost> findByState(State state, PageRequest pageRequest);
}
