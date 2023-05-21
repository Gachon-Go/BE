package gcu.mp.service.deliveryPost.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateDeliveryPostCommentDto {
    Long memberId;
    Long deliveryPostId;
    String content;
}
