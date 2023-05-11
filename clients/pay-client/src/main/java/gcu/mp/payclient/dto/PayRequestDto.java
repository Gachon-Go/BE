package gcu.mp.payclient.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class PayRequestDto {
    long memberId;
    int price;
}
