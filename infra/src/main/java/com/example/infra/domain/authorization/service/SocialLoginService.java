package com.example.infra.domain.authorization.service;

import com.example.core.dto.CreateSocialLogin;
import com.example.infra.domain.authorization.domain.SocialLoginEntity;
import com.example.infra.domain.authorization.repository.SocialLoginJpaRepository;
import com.example.infra.domain.member.domain.MemberEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class SocialLoginService {

    private final SocialLoginJpaRepository socialLoginRepository;

    public void createSocialLogin(MemberEntity memberEntity, CreateSocialLogin createSocialLogin ) {
        SocialLoginEntity socialLoginEntity = SocialLoginEntity.builder()
                .authId(createSocialLogin.getAuthId())
                .authPlatform(createSocialLogin.getAuthPlatform())
                .member(memberEntity)
                .build();
        socialLoginRepository.save(socialLoginEntity);
    }

}
