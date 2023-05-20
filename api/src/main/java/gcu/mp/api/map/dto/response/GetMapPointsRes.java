package gcu.mp.api.map.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetMapPointsRes {
    @Schema(description = "닉네임", example = "peter")
    String nickname;
    @Schema(description = "위도", example = "11.111111")
    double latitude;
    @Schema(description = "경도", example = "11.111111")
    double longitude;
}
