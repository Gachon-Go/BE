package gcu.mp.service.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginMemberDto {
    String nickname;
    long point;
    String ProfileImage;
}
