package com.example.domain.domain.authorization.repository;

import com.example.domain.domain.authorization.domain.SocialLogin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialLoginRepository extends JpaRepository<SocialLogin,Long> {

}
