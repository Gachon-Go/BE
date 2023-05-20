package gcu.mp.domain.orderPost.repository;

import gcu.mp.domain.orderPost.domain.OrderPostComment;
import gcu.mp.domain.orderPost.vo.State;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderPostCommentRepository extends JpaRepository<OrderPostComment, Long> {
    List<OrderPostComment> findByOrderPostIdAndState(Long orderPostId, State state, PageRequest pageRequest);
}
