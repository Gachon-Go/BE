package gcu.mp.api.deliveryPost.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryPostCommentListRes {
    @Schema(description = "댓글 고유번호",example = "1")
    Long commentId;
    @Schema(description = "댓글쓴이", example = "peter")
    String commentWriter;
    @Schema(description = "댓글 내용", example = "사줄게")
    String content;
}
