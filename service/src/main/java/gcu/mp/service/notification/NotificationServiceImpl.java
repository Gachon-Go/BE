package gcu.mp.service.notification;

import gcu.mp.domain.member.domin.Member;
import gcu.mp.domain.notification.domain.Notification;
import gcu.mp.domain.notification.repository.NotificationRepository;
import gcu.mp.domain.notification.vo.State;
import gcu.mp.firebaseclient.FCMNotificationService;
import gcu.mp.service.member.MemberService;
import gcu.mp.service.notification.Dto.GetNotificationListDto;
import gcu.mp.service.notification.Dto.NotificationEventDto;
import gcu.mp.service.notification.Dto.NotificationInfoDto;
import gcu.mp.service.notification.mapper.NotificationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final FCMNotificationService fcmNotificationService;
    private final NotificationMapper notificationMapper;
    private final MemberService memberService;

    public void notificationEvent(NotificationEventDto notificationEventDto) {
        Member member = memberService.getMember(notificationEventDto.getMemberId());
        Notification notification = Notification.builder()
                .content(notificationEventDto.getContent())
                .flag(notificationEventDto.getFlag())
                .state(State.A)
                .build();
        notification.setMember(member);
        notificationRepository.save(notification);
        fcmNotificationService.sendNotificationByToken(notificationMapper.toFCMNotificationRequestDto(notificationEventDto, member.getFcm_id()));
    }

    @Override
    public GetNotificationListDto getNotificationList(Long memberId) {
        LocalDateTime localDateTime = LocalDateTime.now();
        List<Notification> notificationList = notificationRepository.findByMemberIdAndState(memberId,State.A, Sort.by(Sort.Direction.DESC,"id"));
        List<NotificationInfoDto> yesterdayNotificationList = new ArrayList<>();
        List<NotificationInfoDto> pastNotificationList = new ArrayList<>();
        List<NotificationInfoDto> todayNotificationList = new ArrayList<>();
        for (int i = 0; i < notificationList.size(); i++) {
            if (notificationList.get(i).getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
                    .equals(localDateTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))) {
                todayNotificationList.add(NotificationInfoDto.builder()
                        .content(notificationList.get(i).getContent())
                        .flag(notificationList.get(i).getFlag()).build());
            } else if (notificationList.get(i).getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
                    .equals(localDateTime.minusDays(1).format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))) {
                yesterdayNotificationList.add(NotificationInfoDto.builder()
                        .content(notificationList.get(i).getContent())
                        .flag(notificationList.get(i).getFlag()).build());
            } else {
                pastNotificationList.add(NotificationInfoDto.builder()
                        .content(notificationList.get(i).getContent())
                        .flag(notificationList.get(i).getFlag()).build());
            }
        }
        return GetNotificationListDto.builder()
                .yesterDayNotificationList(yesterdayNotificationList)
                .pastNotificationList(pastNotificationList)
                .todayNotificationList(todayNotificationList).build();
    }
}
