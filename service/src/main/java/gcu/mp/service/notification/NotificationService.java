package gcu.mp.service.notification;

import gcu.mp.service.notification.Dto.GetNotificationListDto;
import gcu.mp.service.notification.Dto.NotificationEventDto;

public interface NotificationService {
    void notificationEvent(NotificationEventDto notificationEventDto);

    GetNotificationListDto getNotificationList(Long memberId);
}
