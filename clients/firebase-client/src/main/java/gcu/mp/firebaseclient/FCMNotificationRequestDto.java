package gcu.mp.firebaseclient;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FCMNotificationRequestDto {
    private String fcmCode;
    private String title;
    private String body;
}