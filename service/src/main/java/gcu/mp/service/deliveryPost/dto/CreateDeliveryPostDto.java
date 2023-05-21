package gcu.mp.service.deliveryPost.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateDeliveryPostDto {
    Long memberId;
    String title;
    String content;
    String estimatedTime;
}
