package gcu.mp.domain.member.repository;

import gcu.mp.domain.member.domin.Member;
import gcu.mp.domain.member.vo.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface MemberEntityRepository extends JpaRepository<Member, Long> {
    @Query(value = "" +
            "select u " +
            "from Member u " +
            "where u.status=:status " +
            "and u.profile.nickname=:nickName")
    Optional<Member> findByNicknameAndStatus(
            @Param("nickName") String nickName,
            @Param("status") Status status);
    @Query(value = "" +
            "select u " +
            "from Member u " +
            "where u.status=:status " +
            "and u.socialLogin.provider=:provider " +
            "and u.socialLogin.authId=:auth_id" )
    Optional<Member> findByProviderAndTokenAndUserStatus(
            @Param("provider") String provider,
            @Param("auth_id")String auth_id,
            @Param("status")Status status);
    @Query(value = "" +
            "select u " +
            "from Member u " +
            "where u.status=:status " +
            "and u.profile.email=:email")
    Optional<Member> findByEmailAndStatus(
            @Param("email") String email,
            @Param("status") Status status);

    Optional<Member> findByIdAndStatus(Long id, Status a);
}
