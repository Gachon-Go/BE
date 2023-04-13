package gcu.project.gachongo.domain.member.dto.request;

import gcu.project.gachongo.infra.ouath.core.OAuthType;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateMemberDto {
    String nickname;
    String email;
    String fcm_id;
    OAuthType provider;
    String token;
}
