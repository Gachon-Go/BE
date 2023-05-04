package gcu.mp.api.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LogInMemberResponse {
    @Schema(description = "member Id", example = "1")
    Long id;
    @Schema(description = "JWT", example = "oqu3hotifnaebgfioaebgfoib")
    String jwt;
}
