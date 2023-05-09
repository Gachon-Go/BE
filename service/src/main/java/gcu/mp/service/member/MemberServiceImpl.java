package gcu.mp.service.member;

import gcu.mp.common.exception.BaseException;
import gcu.mp.domain.member.domin.Member;
import gcu.mp.domain.member.domin.SocialLogin;
import gcu.mp.domain.member.repository.MemberEntityRepository;
import gcu.mp.domain.member.vo.Role;
import gcu.mp.domain.member.vo.State;
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
                .state(State.A)
                .fcm_id("123456789")
                .nickname(createMemberDto.getNickname())
                .point(0)
                .email(createMemberDto.getEmail())
                .build();
        SocialLogin socialLogin = SocialLogin.builder()
                .authId(createMemberDto.getProviderId())
                .provider(createMemberDto.getProvider())
                .build();
        member.setSocialLogin(socialLogin);
        memberEntityRepository.save(member);
    }

    public boolean existMember(OauthMemberDto oauthMemberDto) {
        String oauthType = oauthMemberDto.getOauthType();
        String token = oauthMemberDto.getToken();
        Optional<Member> member = memberEntityRepository.findByProviderAndTokenAndUserStatus(oauthType, token, State.A);
        return member.isPresent();
    }

    public boolean existNickname(String nickname) {
        Optional<Member> members = memberEntityRepository.findByNicknameAndState(nickname, State.A);
        return members.isPresent();
    }

    public boolean existEmail(String email) {
        Optional<Member> members = memberEntityRepository.findByEmailAndState(email, State.A);
        return members.isPresent();
    }

    public Long getMemberId(OauthMemberDto oauthMemberDto) {
        String oauthType = oauthMemberDto.getOauthType();
        String token = oauthMemberDto.getToken();
        Member members = memberEntityRepository.findByProviderAndTokenAndUserStatus(oauthType, token, State.A).orElseThrow(() -> new BaseException(NOT_EXIST_MEMBER));
        return members.getId();
    }
    @Transactional
    public void modifyNickname(ModifyNicknameDto modifyNicknameDto) {
        Long id = modifyNicknameDto.getMemberId();
        String nickname = modifyNicknameDto.getNickName();
        Member member = memberEntityRepository.findByIdAndState(id, State.A).orElseThrow(() -> new BaseException(NOT_EXIST_MEMBER));
        member.updateNickname(nickname);
    }
    @Transactional
    public void resignMember(Long memberId) {
        Member members = memberEntityRepository.findByIdAndState(memberId, State.A).orElseThrow(() -> new BaseException(NOT_EXIST_MEMBER));
        members.resignMember();
    }
}