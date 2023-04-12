package gcu.project.gachongo.domain.member.repository;

import gcu.project.gachongo.domain.member.domin.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
}
