package gcu.mp.api.pay.mapper;

import gcu.mp.api.pay.dto.request.PayRequestReq;
import gcu.mp.payclient.dto.KakaoApproveDto;
import gcu.mp.payclient.dto.PayRequestDto;
import gcu.mp.service.pay.dto.PaySuccessDto;
import gcu.mp.service.point.dto.PaysuccessPointDto;
import org.springframework.stereotype.Component;

@Component
public class PayMapper {
    public PayRequestDto toPayRequestDto(long memberId, PayRequestReq payRequestReq){
        return PayRequestDto.builder()
                .price(payRequestReq.getPrice())
                .memberId(memberId).build();
    }
    public PaySuccessDto toPaySuccessDto(KakaoApproveDto kakaoApproveDto){
        return PaySuccessDto.builder()
                .tid(kakaoApproveDto.getTid())
                .totalAmount(kakaoApproveDto.getAmount().getTotal())
                .memberId(Long.parseLong(kakaoApproveDto.getPartner_user_id())).build();
    }
    public PaysuccessPointDto toPaysuccessPointDto(KakaoApproveDto kakaoApproveDto){
        return PaysuccessPointDto.builder()
                .point(kakaoApproveDto.getAmount().getTotal())
                .memberId(Long.parseLong(kakaoApproveDto.getPartner_user_id())).build();
    }
}
