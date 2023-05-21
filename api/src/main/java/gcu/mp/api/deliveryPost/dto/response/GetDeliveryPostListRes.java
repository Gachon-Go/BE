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
public class GetDeliveryPostListRes {
    @Schema(description = "delivery 게시물 고유 번호", example = "1")
    Long deliveryId;
    @Schema(description = "제목", example = "먀관 403호 엽떡 사줘")
    String title;
    @Schema(description = "목표시간", example = "13:00")
    String estimatedTime;
    @Schema(description = "진행 상황", example = "진행중 or 모집완료")
    String progress;
    @Schema(description = "member Id", example = "1")
    int commentNum;
}
