package gcu.mp.service.map.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostMapPointDto {
    long memberId;
    double latitude;
    double longitude;
}
