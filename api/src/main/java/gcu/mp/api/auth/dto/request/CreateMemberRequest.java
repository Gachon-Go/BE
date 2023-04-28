package gcu.mp.api.auth.dto.request;

import gcu.mp.oauthclient.dto.core.OAuthType;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateMemberRequest {
    String nickname;
    String email;
    OAuthType provider;
    String token;
}