package gcu.mp.api.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SendMailReq {
    @Schema(description = "이메일", example = "dsk0820@gachon.ac.kr")
    String email;
}
