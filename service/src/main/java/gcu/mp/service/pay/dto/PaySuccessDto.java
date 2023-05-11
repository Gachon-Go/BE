package gcu.mp.service.pay.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class PaySuccessDto {
    int totalAmount;
    String tid;
    long memberId;
}
