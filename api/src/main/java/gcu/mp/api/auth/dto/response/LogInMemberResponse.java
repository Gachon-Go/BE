package gcu.mp.api.auth.dto.response;

import gcu.mp.service.member.dto.LoginMemberDto;
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
    @Schema(description = "nickname", example = "peter")
    String nickname;
    @Schema(description = "point", example = "10000")
    Long point;

    public LogInMemberResponse(Long memberId, String accessToken, LoginMemberDto loginMemberDto) {
        this.id = memberId;
        this.jwt = accessToken;
        this.nickname = loginMemberDto.getNickname();
        this.point = loginMemberDto.getPoint();
    }
}
