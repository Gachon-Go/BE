package gcu.mp.service.member;

import gcu.mp.domain.member.domin.Member;
import gcu.mp.service.member.dto.CreateMemberDto;
import gcu.mp.service.member.dto.LoginMemberDto;
import gcu.mp.service.member.dto.ModifyNicknameDto;
import gcu.mp.service.member.dto.OauthMemberDto;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {
    boolean existNickname(String nickname);

    void modifyNickname(ModifyNicknameDto toModifyNicknameDto);

    void resignMember(Long memberId);
    Member getMember(Long memberId);

    boolean existEmail(String email);

    boolean existMember(OauthMemberDto toOauthMemberDto);

    void createMember(CreateMemberDto toCreateMemberDto);

    Long getMemberId(OauthMemberDto toOauthMemberDto);

    LoginMemberDto getLonginMember(Long memberId);

    String modifyProfileImage(Long memberId, MultipartFile image);
}
