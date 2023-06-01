package gcu.mp.api.orderPost.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderPostCommentListRes {
    @Schema(description = "댓글 고유번호",example = "1")
    Long commentId;
    @Schema(description = "댓글쓴이", example = "peter")
    String commentWriter;
    @Schema(description = "댓글쓴이 이미지", example = "jpg")
    String commentWriterImage;
    @Schema(description = "댓글 내용", example = "사줄게")
    String content;
    @Schema(description = "수락했는지 안했는지", example = "true, false")
    Boolean isAccept;
}
