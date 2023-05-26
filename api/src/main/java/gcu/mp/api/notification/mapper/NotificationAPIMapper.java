package gcu.mp.api.notification.mapper;

import gcu.mp.api.notification.dto.response.GetNotificationListRes;
import gcu.mp.api.notification.dto.response.NotificationInfo;
import gcu.mp.service.notification.Dto.GetNotificationListDto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class NotificationAPIMapper {
    public GetNotificationListRes toGetNotificationListRes(GetNotificationListDto getNotificationListDto){
        return GetNotificationListRes.builder()
                .todayNotificationList(
                        getNotificationListDto.getTodayNotificationList().stream().map(
                                notificationInfoDto -> NotificationInfo.builder()
                                        .content(notificationInfoDto.getContent())
                                        .flag(notificationInfoDto.getFlag())
                                        .build()
                        ).collect(Collectors.toList())
                )
                .pastNotificationList(
                        getNotificationListDto.getPastNotificationList().stream().map(
                                notificationInfoDto -> NotificationInfo.builder()
                                        .content(notificationInfoDto.getContent())
                                        .flag(notificationInfoDto.getFlag())
                                        .build()
                        ).collect(Collectors.toList())
                )
                .yesterDayNotificationList(
                        getNotificationListDto.getYesterDayNotificationList().stream().map(
                                notificationInfoDto -> NotificationInfo.builder()
                                        .content(notificationInfoDto.getContent())
                                        .flag(notificationInfoDto.getFlag())
                                        .build()
                        ).collect(Collectors.toList())
                ).build();
    }
}
