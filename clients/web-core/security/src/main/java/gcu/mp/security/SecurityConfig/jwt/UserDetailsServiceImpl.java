package gcu.mp.security.SecurityConfig.jwt;

import gcu.mp.domain.member.domin.Member;
import gcu.mp.domain.member.repository.MemberEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final MemberEntityRepository memberEntityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<Member> member = memberEntityRepository.findById(Long.parseLong(username));
        return User.withUsername(username)
                .password(member.get().getId().toString())
                .authorities(AuthorityUtils.NO_AUTHORITIES)
                .build();
    }
}