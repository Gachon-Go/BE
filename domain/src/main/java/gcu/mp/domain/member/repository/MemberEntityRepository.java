package gcu.mp.domain.member.repository;

import gcu.mp.domain.member.domin.Member;
import gcu.mp.domain.member.vo.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface MemberEntityRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByNicknameAndState(String nickName, State state);
    @Query(value = "" +
            "select u " +
            "from Member u " +
            "where u.state=:status " +
            "and u.socialLogin.provider=:provider " +
            "and u.socialLogin.authId=:auth_id" )
    Optional<Member> findByProviderAndTokenAndUserStatus(
            @Param("provider") String provider,
            @Param("auth_id")String auth_id,
            @Param("status") State state);

    Optional<Member> findByEmailAndState(String email, State state);

    Optional<Member> findByIdAndState(Long id, State a);
}
