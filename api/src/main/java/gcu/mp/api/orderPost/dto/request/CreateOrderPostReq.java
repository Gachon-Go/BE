package gcu.mp.api.orderPost.dto.request;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateOrderPostReq {
    String title;
    String content;
    String estimatedTime;
}