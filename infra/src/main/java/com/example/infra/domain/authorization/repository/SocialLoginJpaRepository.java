package com.example.infra.domain.authorization.repository;

import com.example.infra.domain.authorization.domain.SocialLoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialLoginJpaRepository extends JpaRepository<SocialLoginEntity,Long> {

}
