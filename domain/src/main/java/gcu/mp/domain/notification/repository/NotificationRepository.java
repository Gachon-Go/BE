package gcu.mp.domain.notification.repository;

import gcu.mp.domain.notification.domain.Notification;
import gcu.mp.domain.notification.vo.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {

    List<Notification> findByMemberIdAndState(Long memberId, State state);
}
