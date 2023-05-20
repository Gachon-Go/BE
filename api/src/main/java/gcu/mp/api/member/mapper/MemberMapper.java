package gcu.mp.api.member.mapper;

import gcu.mp.api.member.dto.request.ModifyNicknameReq;
import gcu.mp.api.member.dto.response.MyPageRes;
import gcu.mp.service.member.dto.ModifyNicknameDto;
import gcu.mp.service.member.dto.MyPageDto;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public ModifyNicknameDto toModifyNicknameDto(ModifyNicknameReq modifyNicknameReq, Long memberId) {
        return ModifyNicknameDto.builder()
                .nickName(modifyNicknameReq.getNickname())
                .memberId(memberId).build();
    }

    public MyPageRes toMyPageRes(MyPageDto myPageDto) {
        return MyPageRes.builder()
                .deliveryNum(myPageDto.getDeliveryNum())
                .point(myPageDto.getPoint())
                .nickname(myPageDto.getNickname())
                .orderNum(myPageDto.getOrderNum())
                .postNum(myPageDto.getPostNum())
                .build();
    }
}
