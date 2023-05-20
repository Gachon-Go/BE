package gcu.mp.service.map.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetMapPointDto {
    String nickname;
    double latitude;
    double longitude;
}
