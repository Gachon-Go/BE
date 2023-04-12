package gcu.project.gachongo.domain.member.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SendMailRequest {
    String email;
}
