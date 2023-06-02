package gcu.mp.service.notification.mapper;

import gcu.mp.firebaseclient.FCMNotificationRequestDto;
import gcu.mp.service.notification.Dto.NotificationEventDto;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {
    public FCMNotificationRequestDto toFCMNotificationRequestDto(NotificationEventDto notificationEventDto, String fcmId) {
        return FCMNotificationRequestDto.builder()
                .fcmCode(fcmId)
                .body(notificationEventDto.getContent())
                .title("Gachon Go").build();
    }
}