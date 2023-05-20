package gcu.mp.api.map.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostMapPointReq {
    @Schema(description = "종류", example = "order 또는 delivery")
    String purpose;
    @Schema(description = "포스트 id", example = "1")
    long postId;
    @Schema(description = "위도", example = "37.484111")
    double latitude;
    @Schema(description = "경도", example = "126.929861")
    double longitude;
}
