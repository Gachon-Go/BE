package gcu.mp.service.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MyPageDto {
    String nickname;
    Long point;
    int orderNum;
    int deliveryNum;
    int postNum;
}
