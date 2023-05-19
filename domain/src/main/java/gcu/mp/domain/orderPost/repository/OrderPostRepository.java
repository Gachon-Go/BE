package gcu.mp.domain.orderPost.repository;

import gcu.mp.domain.orderPost.domain.OrderPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderPostRepository extends JpaRepository<OrderPost,Long> {
}
