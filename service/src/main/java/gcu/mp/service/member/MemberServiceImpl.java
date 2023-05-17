package gcu.mp.service.member;

import gcu.mp.common.exception.BaseException;
import gcu.mp.domain.member.domin.Member;
import gcu.mp.domain.member.domin.SocialLogin;
import gcu.mp.domain.member.repository.MemberEntityRepository;
import gcu.mp.domain.member.vo.Role;
import gcu.mp.domain.member.vo.State;
import gcu.mp.s3client.S3Service;
import gcu.mp.service.member.dto.CreateMemberDto;
import gcu.mp.service.member.dto.LoginMemberDto;
import gcu.mp.service.member.dto.ModifyNicknameDto;
import gcu.mp.service.member.dto.OauthMemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static gcu.mp.common.api.BaseResponseStatus.NOT_EXIST_MEMBER;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberServiceImpl implements MemberService {
    private final MemberEntityRepository memberEntityRepository;
    private final S3Service s3Service;
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
                .image(createMemberDto.getImageUrl())
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

    @Override
    public LoginMemberDto getLonginMember(Long memberId) {
        Member member = getMember(memberId);
        return LoginMemberDto.builder()
                .nickname(member.getNickname())
                .point(member.getPoint()).build();
    }

    @Override
    @Transactional
    public String modifyProfileImage(Long memberId, MultipartFile image) {
        Member member = getMember(memberId);
        String s3ImageUrl = s3Service.uploadProfileImage(image);
        member.setProfileImage(s3ImageUrl);
        return s3ImageUrl;
    }

    @Transactional
    public void modifyNickname(ModifyNicknameDto modifyNicknameDto) {
        Long id = modifyNicknameDto.getMemberId();
        String nickname = modifyNicknameDto.getNickName();
        Member member = getMember(modifyNicknameDto.getMemberId());
        member.updateNickname(nickname);
    }

    @Transactional
    public void resignMember(Long memberId) {
        Member members = memberEntityRepository.findByIdAndState(memberId, State.A).orElseThrow(() -> new BaseException(NOT_EXIST_MEMBER));
        members.resignMember();
    }

    @Override
    public Member getMember(Long memberId) {
        return memberEntityRepository.findByIdAndState(memberId, State.A).orElseThrow(() -> new BaseException(NOT_EXIST_MEMBER));
    }
}