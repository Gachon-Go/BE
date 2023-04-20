package gcu.project.gachongo.domain.member.repository;

import gcu.project.gachongo.domain.member.domin.Member;
import gcu.project.gachongo.domain.member.vo.Status;
import gcu.project.gachongo.service.oauth.dto.core.OAuthType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
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
            @Param("provider") OAuthType provider,
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
}
