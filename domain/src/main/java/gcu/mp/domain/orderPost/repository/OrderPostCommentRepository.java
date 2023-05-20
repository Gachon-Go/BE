package gcu.mp.domain.orderPost.repository;

import gcu.mp.domain.orderPost.domain.OrderPostComment;
import gcu.mp.domain.orderPost.vo.State;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderPostCommentRepository extends JpaRepository<OrderPostComment, Long> {
    List<OrderPostComment> findByOrderPostIdAndState(Long orderPostId, State state, PageRequest pageRequest);

    Optional<OrderPostComment> findByIdAndState(Long commentId, State state);
}
