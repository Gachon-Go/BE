package gcu.mp.api.map.mapper;

import gcu.mp.api.map.dto.request.PostMapPointReq;
import gcu.mp.service.map.dto.PostMapPointDto;
import org.springframework.stereotype.Component;

@Component
public class MapMapper {
    public PostMapPointDto toPostMapPointDto(long memberId, PostMapPointReq postMapPointReq){
        return PostMapPointDto.builder()
                .postId(postMapPointReq.getPostId())
                .latitude(postMapPointReq.getLatitude())
                .longitude(postMapPointReq.getLongitude())
                .memberId(memberId).build();
    }
}
