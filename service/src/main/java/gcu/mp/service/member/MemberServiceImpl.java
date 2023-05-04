package gcu.mp.service.member;

import gcu.mp.common.exception.BaseException;
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

import static gcu.mp.common.api.BaseResponseStatus.NOT_EXIST_MEMBER;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberServiceImpl implements MemberService{
    private final MemberEntityRepository memberEntityRepository;

    @Transactional
    @Override
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
        Member members = memberEntityRepository.findByProviderAndTokenAndUserStatus(oauthType, token, Status.A).orElseThrow(() -> new BaseException(NOT_EXIST_MEMBER));
        return members.getId();
    }
    @Transactional
    public void modifyNickname(ModifyNicknameDto modifyNicknameDto) {
        Long id = modifyNicknameDto.getMemberId();
        String nickName = modifyNicknameDto.getNickName();
        Member member = memberEntityRepository.findByIdAndStatus(id, Status.A).orElseThrow(() -> new BaseException(NOT_EXIST_MEMBER));
        member.getProfile().updateNickname(nickName);
    }
    @Transactional
    public void resignMember(Long memberId) {
        Member members = memberEntityRepository.findByIdAndStatus(memberId, Status.A).orElseThrow(() -> new BaseException(NOT_EXIST_MEMBER));
        members.resignMember();
    }
}