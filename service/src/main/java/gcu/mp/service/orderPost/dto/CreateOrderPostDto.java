package gcu.mp.service.orderPost.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
public class CreateOrderPostDto {
    Long memberId;
    String title;
    String content;
    String estimatedTime;
}
