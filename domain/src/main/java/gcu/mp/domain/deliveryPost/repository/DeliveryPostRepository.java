package gcu.mp.domain.deliveryPost.repository;

import gcu.mp.domain.deliveryPost.domain.DeliveryPost;
import gcu.mp.domain.member.domin.Member;
import gcu.mp.domain.member.vo.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryPostRepository extends JpaRepository<DeliveryPost, Long> {

}
