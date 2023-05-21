package gcu.mp.service.deliveryPost.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetDeliveryPostListDto {
    Long deliveryPostId;
    String title;
    String estimatedTime;
    String progress;
    int commentNum;
}
