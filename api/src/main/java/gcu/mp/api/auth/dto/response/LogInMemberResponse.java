package gcu.mp.api.auth.dto.response;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LogInMemberResponse {
    String jwt;
}
