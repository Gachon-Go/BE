package gcu.mp.api.member.mapper;

import gcu.mp.api.member.dto.request.ModifyNicknameRequest;
import gcu.mp.service.member.dto.ModifyNicknameDto;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public ModifyNicknameDto toModifyNicknameDto(ModifyNicknameRequest modifyNicknameRequest, Long memberId) {
        return ModifyNicknameDto.builder()
                .nickName(modifyNicknameRequest.getNickname())
                .memberId(memberId).build();
    }
}
