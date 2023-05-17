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
    @Schema(description = "image", example = "https://gachongo-bucket.s3.ap-northeast-2.amazonaws.com/afea8097-803c-47a5-9efa-5f47b342e8f0.png")
    String profileImage;
    public LogInMemberResponse(Long memberId, String accessToken, LoginMemberDto loginMemberDto) {
        this.id = memberId;
        this.jwt = accessToken;
        this.nickname = loginMemberDto.getNickname();
        this.point = loginMemberDto.getPoint();
        this.profileImage = loginMemberDto.getProfileImage();
    }
}
