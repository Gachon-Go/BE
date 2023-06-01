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
public class GetOrderPostDetailRes {
    @Schema(description = "본인 게시물이면 true 아니면 false", example = "true 또는 false")
    boolean mine;
    @Schema(description = "글쓴이", example = "peter")
    String writer;
    @Schema(description = "제목", example = "먀관 403호 엽떡 사줘")
    String title;
    @Schema(description = "내용", example = "얼른 사줘")
    String content;
    @Schema(description = "목표시간", example = "13:00")
    String estimatedTime;
    @Schema(description = "댓글 수", example = "10")
    int commentNum;
    @Schema(description = "글쓴이 이미지", example = "img")
    String writerImage;
}
