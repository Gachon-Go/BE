package gcu.mp.domain.deliveryPost.repository;

import gcu.mp.domain.deliveryPost.domain.DeliveryPostComment;
import gcu.mp.domain.orderPost.domain.OrderPostComment;
import gcu.mp.domain.orderPost.vo.State;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DeliveryPostCommentRepository extends JpaRepository<DeliveryPostComment, Long> {
}
