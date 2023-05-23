package gcu.mp.api.point.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetPointTransactionIdRes {
    @Schema(description = "고유 번호", example = "1028362")
    String TransactionId;
}
