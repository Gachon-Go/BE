package gcu.mp.service.orderPost.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateOrderPostCommentDto {
    Long memberId;
    Long orderPostId;
    String content;
}
