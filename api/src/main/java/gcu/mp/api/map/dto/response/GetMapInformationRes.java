package gcu.mp.api.map.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetMapInformationRes {
    @Schema(description = "포스트 id", example = "1")
    Long postId;
    @Schema(description = "종류", example = "order 또는 delivery")
    String purpose;
    List<MapPoint> mapPoints = new ArrayList<>();
}
