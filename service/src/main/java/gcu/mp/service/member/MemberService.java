package gcu.mp.service.member;

import gcu.mp.domain.member.domin.Member;
import gcu.mp.service.member.dto.*;
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

    LoginMemberDto getLonginMember(Long memberId,String fcmId);

    String modifyProfileImage(Long memberId, MultipartFile image);

    MyPageDto getMyPage(Long memberId);
}
