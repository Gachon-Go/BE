package gcu.mp.domain.point.repository;

import gcu.mp.domain.point.domin.PointHistory;
import gcu.mp.domain.point.vo.State;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {

    List<PointHistory> findByMemberIdAndState(Long memberId, State state, Pageable request);
}
