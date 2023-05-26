package gcu.mp.service.notification.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NotificationEventDto {
    Long memberId;
    String content;
    int flag;
}
