package gcu.mp.service.map;

import gcu.mp.service.map.dto.GetMapPointDto;
import gcu.mp.service.map.dto.PostMapPointDto;

import java.util.List;

public interface MapService {
    void postMapPoint(PostMapPointDto postMapPointDto);

    List<GetMapPointDto> getMapPointList(Long postId);
}
