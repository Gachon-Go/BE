package gcu.mp.api.map.dto.request;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostMapPointReq {
    long postId;
    double latitude;
    double longitude;
}
