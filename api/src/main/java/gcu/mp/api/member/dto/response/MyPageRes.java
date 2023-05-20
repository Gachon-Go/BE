package gcu.mp.api.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyPageRes {
    @Schema(description = "닉네임", example = "peter")
    String nickname;
    @Schema(description = "보유한 포인트", example = "10000")
    Long point;
    @Schema(description = "주문 횟수", example = "10")
    int orderNum;
    @Schema(description = "배달 횟수", example = "99")
    int deliveryNum;
    @Schema(description = "게시글 수", example = "123")
    int postNum;
}
