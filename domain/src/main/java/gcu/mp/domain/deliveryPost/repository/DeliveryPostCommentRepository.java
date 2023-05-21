package gcu.mp.domain.deliveryPost.repository;

import gcu.mp.domain.deliveryPost.domain.DeliveryPostComment;
import gcu.mp.domain.deliveryPost.vo.State;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryPostCommentRepository extends JpaRepository<DeliveryPostComment, Long> {
    Optional<DeliveryPostComment> findByIdAndState(Long commentId, State state);

    List<DeliveryPostComment> findByDeliveryPostIdAndState(Long deliveryPostId, State state, PageRequest pageRequest);
}
