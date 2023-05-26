package gcu.mp.firebaseclient;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import gcu.mp.common.api.BaseResponseStatus;
import gcu.mp.common.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class FCMNotificationService {

    private final FirebaseMessaging firebaseMessaging;

    @Async
    public void sendNotificationByToken(FCMNotificationRequestDto requestDto) {

        Notification notification = Notification.builder()
                .setTitle(requestDto.getTitle())
                .setBody(requestDto.getBody())
                .build();

        Message message = Message.builder()
                .setToken(requestDto.getFcmCode())
                .setNotification(notification)
                .build();
            try {
                firebaseMessaging.send(message);
            } catch (FirebaseMessagingException e) {
                log.error("fcm 메세지 전송 실패: {}",e.getMessagingErrorCode());
            }
    }

}