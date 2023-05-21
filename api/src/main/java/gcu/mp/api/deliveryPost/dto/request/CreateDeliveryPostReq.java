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
public class CreateDeliveryPostReq {
    @Schema(description = "제목", example = "먀관 403호 엽떡 사줘")
    String title;
    @Schema(description = "내용", example = "얼른 사줘")
    String content;
    @Schema(description = "목표시간", example = "13:00")
    String estimatedTime;
}
