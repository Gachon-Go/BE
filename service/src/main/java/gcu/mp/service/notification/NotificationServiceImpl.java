package gcu.mp.service.notification;

import gcu.mp.domain.member.domin.Member;
import gcu.mp.domain.notification.domain.Notification;
import gcu.mp.domain.notification.repository.NotificationRepository;
import gcu.mp.domain.notification.vo.State;
import gcu.mp.firebaseclient.FCMNotificationService;
import gcu.mp.service.member.MemberService;
import gcu.mp.service.notification.Dto.NotificationEventDto;
import gcu.mp.service.notification.mapper.NotificationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        fcmNotificationService.sendNotificationByToken(notificationMapper.toFCMNotificationRequestDto(notificationEventDto,member.getFcm_id()));
    }
}
