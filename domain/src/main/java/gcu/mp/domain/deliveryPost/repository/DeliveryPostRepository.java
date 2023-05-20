package gcu.mp.domain.deliveryPost.repository;

import gcu.mp.domain.deliveryPost.domain.DeliveryPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DeliveryPostRepository extends JpaRepository<DeliveryPost, Long> {

}
