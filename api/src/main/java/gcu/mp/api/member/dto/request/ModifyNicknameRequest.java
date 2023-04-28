package gcu.mp.api.member.dto.request;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ModifyNicknameRequest {
    String nickname;
}
