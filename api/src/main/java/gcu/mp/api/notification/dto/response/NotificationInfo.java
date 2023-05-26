package gcu.mp.api.notification.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationInfo {
    @Schema(description = "알림내용", example = "내가 작성한 게시물 먀관 403호 엽떡 사줘에 새로운 댓글이 작성되었습니다.")
    String content;
    @Schema(description = "알림 구분자", example = "1. 배달기사선택, 2. 포인트, 3. 배달완려, 4. 댓글")
    int flag;
}
