package gcu.mp.api.member.dto.request;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModifyNicknameReq {
    String nickname;
}
