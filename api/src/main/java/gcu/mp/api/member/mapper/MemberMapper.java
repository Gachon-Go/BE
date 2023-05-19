package gcu.mp.api.member.mapper;

import gcu.mp.api.member.dto.request.ModifyNicknameReq;
import gcu.mp.service.member.dto.ModifyNicknameDto;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public ModifyNicknameDto toModifyNicknameDto(ModifyNicknameReq modifyNicknameReq, Long memberId) {
        return ModifyNicknameDto.builder()
                .nickName(modifyNicknameReq.getNickname())
                .memberId(memberId).build();
    }
}
