package gcu.mp.service.deliveryPost.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetDeliveryPostDetailDto {
    boolean mine;
    String writer;
    String title;
    String content;
    String estimatedTime;
    int commentNum;
    String writerImage;
}
