package gcu.mp.service.map;

import gcu.mp.service.map.dto.GetMapInformationDto;
import gcu.mp.service.map.dto.PostMapPointDto;

import java.util.List;

public interface MapService {
    void postMapPoint(PostMapPointDto postMapPointDto);

    GetMapInformationDto getMapInformation(Long memberId);
}
