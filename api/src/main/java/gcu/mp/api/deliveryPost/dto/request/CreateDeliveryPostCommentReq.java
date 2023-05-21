package gcu.mp.api.deliveryPost.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateDeliveryPostCommentReq {
    @Schema(description = "댓글 내용", example = "사줄게")
    String content;
}
