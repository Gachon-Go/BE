package gcu.project.gachongo.api.auth.dto.request;

import gcu.project.gachongo.service.oauth.dto.core.OAuthType;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateMemberRequest {
    String nickname;
    String email;
    String fcm_id;
    OAuthType provider;
    String token;
}