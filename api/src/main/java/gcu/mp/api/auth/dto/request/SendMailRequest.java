package gcu.mp.api.auth.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SendMailRequest {
    String email;
}
