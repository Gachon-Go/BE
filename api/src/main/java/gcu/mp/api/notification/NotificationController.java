package gcu.mp.api.notification;

import gcu.mp.api.notification.dto.response.GetNotificationListRes;
import gcu.mp.api.notification.mapper.NotificationAPIMapper;
import gcu.mp.common.api.BaseResponse;
import gcu.mp.common.exception.BaseException;
import gcu.mp.firebaseclient.FCMNotificationRequestDto;
import gcu.mp.firebaseclient.FCMNotificationService;
import gcu.mp.service.notification.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@Tag(name = "푸시 알림", description = "FCM Notification 관련 api 입니다.")
@RequiredArgsConstructor
@RestController
@RequestMapping("notification")
public class NotificationController {

    private final FCMNotificationService fcmNotificationService;
    private final NotificationService notificationService;
    private final NotificationAPIMapper notificationAPIMapper;
    @Operation(summary = "알림 보내기(TEST 전용)")
    @PostMapping()
    public ResponseEntity<BaseResponse<String>> sendNotificationByToken(@RequestBody FCMNotificationRequestDto requestDto) {
        try {
            fcmNotificationService.sendNotificationByToken(requestDto);
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>("전송했어요"));
        } catch (BaseException e) {
            return ResponseEntity.status(e.getStatus().getHttpCode()).body(new BaseResponse<>(e.getStatus()));
        }
    }
    @Operation(summary = "알림 리스트 조회")
    @GetMapping()
    public ResponseEntity<BaseResponse<GetNotificationListRes>> getNotificationList() {
        try {
            Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
            Long memberId = Long.parseLong(loggedInUser.getName());
            GetNotificationListRes getNotificationListRes = notificationAPIMapper.toGetNotificationListRes(notificationService.getNotificationList(memberId));
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(getNotificationListRes));
        } catch (BaseException e) {
            return ResponseEntity.status(e.getStatus().getHttpCode()).body(new BaseResponse<>(e.getStatus()));
        }
    }
}