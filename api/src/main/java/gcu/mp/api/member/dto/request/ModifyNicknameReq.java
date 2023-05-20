package gcu.mp.api.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModifyNicknameReq {
    @Schema(description = "닉네임", example = "peeter")
    String nickname;
}
