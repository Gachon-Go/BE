package gcu.mp.service.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OauthMemberDto {
    String oauthType;
    String Token;

}