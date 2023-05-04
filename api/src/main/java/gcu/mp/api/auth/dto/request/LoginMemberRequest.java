package gcu.mp.api.auth.dto.request;

import gcu.mp.oauthclient.dto.core.OAuthType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginMemberRequest {
    @Schema(description = "알림 id", example = "qiuheroifqbwefiqw")
    String fcmId;
    @Schema(description = "소셜로그인 제공자", example = "Kakao")
    OAuthType provider;
    @Schema(description = "소셜로그인 토큰", example = "pqohrc9uwhfiouqccr987q230hrpqnorf")
    String token;
}
