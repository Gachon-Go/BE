package gcu.mp.api.point.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetPointTransactionIdReq {
    @Schema(description = "거래할 포인트", example = "10000")
    Long point;
}
