package com.example.infra.domain.member.repository;


import com.example.infra.domain.member.domain.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {

}
