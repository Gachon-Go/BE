package gcu.mp.api.map.mapper;

import gcu.mp.api.map.dto.request.PostMapPointReq;
import gcu.mp.api.map.dto.response.GetMapInformationRes;
import gcu.mp.api.map.dto.response.MapPoint;
import gcu.mp.service.map.dto.GetMapInformationDto;
import gcu.mp.service.map.dto.PostMapPointDto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MapMapper {
    public PostMapPointDto toPostMapPointDto(long memberId, PostMapPointReq postMapPointReq) {
        return PostMapPointDto.builder()
                .latitude(postMapPointReq.getLatitude())
                .longitude(postMapPointReq.getLongitude())
                .memberId(memberId).build();
    }

    public GetMapInformationRes toGetMapPointsResList(GetMapInformationDto getMapInformationDto) {
        return GetMapInformationRes.builder()
                .mapPoints(getMapInformationDto.getMapPointDtoList().stream().map(
                        mapPointDto -> MapPoint.builder()
                                .nickname(mapPointDto.getNickname())
                                .latitude(mapPointDto.getLatitude())
                                .longitude(mapPointDto.getLongitude()).build()
                ).collect(Collectors.toList()))
                .postId(getMapInformationDto.getPostId())
                .purpose(
                        getMapInformationDto.getPurpose()
                )
                .build();
    }
}
