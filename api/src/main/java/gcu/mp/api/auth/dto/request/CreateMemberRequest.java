package gcu.mp.api.auth.dto.request;

import gcu.mp.oauthclient.dto.core.OAuthType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateMemberRequest {
    @Schema(description = "닉네임", example = "peter")
    String nickname;
    @Schema(description = "이메일", example = "dsk0820@gachon.ac.kr")
    String email;
    @Schema(description = "소셜로그인 제공자", example = "Kakao")
    OAuthType provider;
    @Schema(description = "소셜로그인 토큰", example = "pqohrc9uwhfiouqccr987q230hrpqnorf")
    String token;
}