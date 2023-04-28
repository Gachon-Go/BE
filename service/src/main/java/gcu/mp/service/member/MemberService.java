package gcu.mp.service.member;

import gcu.mp.domain.entity.BaseEntity;
import gcu.mp.domain.member.domin.Member;
import gcu.mp.domain.member.domin.Profile;
import gcu.mp.domain.member.domin.SocialLogin;
import gcu.mp.domain.member.repository.MemberEntityRepository;
import gcu.mp.domain.member.vo.Role;
import gcu.mp.domain.member.vo.Status;
import gcu.mp.service.member.dto.CreateMemberDto;
import gcu.mp.service.member.dto.ModifyNicknameDto;
import gcu.mp.service.member.dto.OauthMemberDto;
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
    private final MemberEntityRepository memberEntityRepository;

    @Transactional
    public void createMember(CreateMemberDto createMemberDto) {
        Member member = Member.builder()
                .role(Role.USER)
                .status(Status.A)
                .build();
        Profile profile = Profile.builder()
                .email(createMemberDto.getEmail())
                .nickname(createMemberDto.getNickname())
                .build();
        SocialLogin socialLogin = SocialLogin.builder()
                .authId(createMemberDto.getProviderId())
                .provider(createMemberDto.getProvider())
                .build();
        member.setProfile(profile);
        member.setSocialLogin(socialLogin);
        memberEntityRepository.save(member);
    }

    public boolean existMember(OauthMemberDto oauthMemberDto) {
        String oauthType = oauthMemberDto.getOauthType();
        String token = oauthMemberDto.getToken();
        Optional<Member> member = memberEntityRepository.findByProviderAndTokenAndUserStatus(oauthType, token, Status.A);
        return member.isPresent();
    }

    public boolean existNickname(String nickname) {
        Optional<Member> members = memberEntityRepository.findByNicknameAndStatus(nickname, Status.A);
        return members.isPresent();
    }

    public boolean existEmail(String email) {
        Optional<Member> members = memberEntityRepository.findByEmailAndStatus(email, Status.A);
        return members.isPresent();
    }

    public Long getMemberId(OauthMemberDto oauthMemberDto) {
        String oauthType = oauthMemberDto.getOauthType();
        String token = oauthMemberDto.getToken();
        Optional<Member> members = memberEntityRepository.findByProviderAndTokenAndUserStatus(oauthType, token, Status.A);
        return members.map(BaseEntity::getId).orElse(null);
    }

    public void modifyNickname(ModifyNicknameDto modifyNicknameDto) {
        Long id = modifyNicknameDto.getMemberId();
        String nickName = modifyNicknameDto.getNickName();
        Optional<Member> member = memberEntityRepository.findByIdAndStatus(id, Status.A);
        member.get().getProfile().updateNickname(nickName);
    }

}