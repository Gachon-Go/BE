package gcu.mp.api.point.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointHistoryListRes {
    @Schema(description = "날짜", example = "2023.05.06")
    String time;
    @Schema(description = "이력 내용", example = "포인트 충전")
    String content;
    @Schema(description = "포인트", example = "+30000")
    String point;
}
