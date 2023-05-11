package gcu.mp.domain.pay.repository;

import gcu.mp.domain.pay.domain.PayHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayHistoryRepository extends JpaRepository<PayHistory,Long> {
}
