package com.example.infra.system.config.securityConfig.jwt;


import com.example.infra.domain.member.domain.MemberEntity;
import com.example.infra.domain.member.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        MemberEntity memberEntity = memberJpaRepository.findById(Long.parseLong(username))
                .orElseThrow(() -> new UsernameNotFoundException("username = " + username));
        return User.withUsername(username)
                .password(memberEntity.getId().toString())
                .authorities(AuthorityUtils.NO_AUTHORITIES)
                .build();
    }
}