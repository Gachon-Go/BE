package gcu.mp.service.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateMemberDto {
    String fcm_id;
    String email;
    String nickname;
    String providerId;
    String provider;
}
