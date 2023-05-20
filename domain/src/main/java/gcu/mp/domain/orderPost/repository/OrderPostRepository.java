package gcu.mp.domain.orderPost.repository;

import gcu.mp.domain.orderPost.domain.OrderPost;
import gcu.mp.domain.orderPost.vo.State;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderPostRepository extends JpaRepository<OrderPost,Long> {
    List<OrderPost> findByState(State state, PageRequest pageRequest);

    OrderPost findByIdAndState(Long orderPostId, State state);
}
