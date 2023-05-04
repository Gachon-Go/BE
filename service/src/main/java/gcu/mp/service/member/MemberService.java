package gcu.mp.service.member;

import gcu.mp.service.member.dto.CreateMemberDto;
import gcu.mp.service.member.dto.ModifyNicknameDto;
import gcu.mp.service.member.dto.OauthMemberDto;

public interface MemberService {
    boolean existNickname(String nickname);

    void modifyNickname(ModifyNicknameDto toModifyNicknameDto);

    void resignMember(Long memberId);

    boolean existEmail(String email);

    boolean existMember(OauthMemberDto toOauthMemberDto);

    void createMember(CreateMemberDto toCreateMemberDto);

    Long getMemberId(OauthMemberDto toOauthMemberDto);
}
