package gcu.mp.api.orderPost.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderPostCommentReq {
    @Schema(description = "댓글 내용", example = "사줄게")
    String content;
}
