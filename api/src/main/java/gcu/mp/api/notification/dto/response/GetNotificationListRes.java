package gcu.mp.api.notification.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetNotificationListRes {
    List<NotificationInfo> todayNotificationList = new ArrayList<>();
    List<NotificationInfo> yesterdayNotificationList = new ArrayList<>();
    List<NotificationInfo> pastNotificationList = new ArrayList<>();
}
