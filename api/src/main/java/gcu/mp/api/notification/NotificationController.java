package gcu.mp.api.notification;

import gcu.mp.common.api.BaseResponse;
import gcu.mp.common.exception.BaseException;
import gcu.mp.firebaseclient.FCMNotificationRequestDto;
import gcu.mp.firebaseclient.FCMNotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.OutputKeys;

@Tag(name = "푸시 알림", description = "(TEST 전용) FCM Notification 관련 api 입니다.")
@RequiredArgsConstructor
@RestController
@RequestMapping("notification")
public class NotificationController {

    private final FCMNotificationService fcmNotificationService;

    @Operation(summary = "알림 보내기")
    @PostMapping()
    public ResponseEntity<BaseResponse<String>> sendNotificationByToken(@RequestBody FCMNotificationRequestDto requestDto) {
        try {
            fcmNotificationService.sendNotificationByToken(requestDto);
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>("전송했어요"));
        } catch (BaseException e) {
            return ResponseEntity.status(e.getStatus().getHttpCode()).body(new BaseResponse<>(e.getStatus()));
        }
    }
}