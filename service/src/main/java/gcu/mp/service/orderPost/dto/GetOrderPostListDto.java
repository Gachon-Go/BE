package gcu.mp.service.orderPost.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetOrderPostListDto {
    Long orderId;
    String title;
    String estimatedTime;
    String progress;
    int commentNum;
}
