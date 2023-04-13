package gcu.project.gachongo.domain.member.service;

import gcu.project.gachongo.domain.member.domin.Member;
import gcu.project.gachongo.domain.member.domin.Profile;
import gcu.project.gachongo.domain.member.domin.SocialLogin;
import gcu.project.gachongo.domain.member.dto.request.CreateMemberDto;
import gcu.project.gachongo.domain.member.repository.MemberRepository;
import gcu.project.gachongo.domain.member.vo.Role;
import gcu.project.gachongo.domain.member.vo.Status;
import gcu.project.gachongo.infra.ouath.core.OAuth2UserInfo;
import gcu.project.gachongo.infra.ouath.core.OAuthType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    @Transactional
    public void createMember(CreateMemberDto createMemberDto, OAuth2UserInfo oAuth2UserInfo) {
        Member member = Member.builder()
                .fcm_id(createMemberDto.getFcm_id())
                .role(Role.USER)
                .status(Status.A)
                .build();
        Profile profile = Profile.builder()
                .email(createMemberDto.getEmail())
                .nickname(createMemberDto.getNickname())
                .build();
        SocialLogin socialLogin = SocialLogin.builder()
                .authId(oAuth2UserInfo.getProviderId())
                .provider(oAuth2UserInfo.getProvider())
                .build();
        member.setProfile(profile);
        member.setSocialLogin(socialLogin);
        memberRepository.save(member);
    }
    public boolean existMember(OAuthType oauthType, String token){
        Optional<Member> member = memberRepository.findByProviderAndTokenAndUserStatus(oauthType,token,Status.A);
        return member.isPresent();
    }
    public boolean existNickname(String nickname){
        Optional<Member> members = memberRepository.findByNicknameAndStatus(nickname, Status.A);
        return members.isPresent();
    }
    public boolean existEmail(String email){
        Optional<Member> members = memberRepository.findByEmailAndStatus(email, Status.A);
        return members.isPresent();
    }
}