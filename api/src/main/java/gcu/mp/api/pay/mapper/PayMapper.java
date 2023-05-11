package gcu.mp.api.pay.mapper;

import gcu.mp.api.pay.dto.request.PayRequestReq;
import gcu.mp.payclient.dto.PayRequestDto;
import org.springframework.stereotype.Component;

@Component
public class PayMapper {
    public PayRequestDto toPayRequestDto(long memberId, PayRequestReq payRequestReq){
        return PayRequestDto.builder()
                .price(payRequestReq.getPrice())
                .memberId(memberId).build();
    }
}
