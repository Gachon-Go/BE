package gcu.mp.api.map.mapper;

import gcu.mp.api.map.dto.request.PostMapPointReq;
import gcu.mp.api.map.dto.response.GetMapPointsRes;
import gcu.mp.service.map.dto.GetMapPointDto;
import gcu.mp.service.map.dto.PostMapPointDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MapMapper {
    public PostMapPointDto toPostMapPointDto(long memberId, PostMapPointReq postMapPointReq) {
        return PostMapPointDto.builder()
                .postId(postMapPointReq.getPostId())
                .purpose(postMapPointReq.getPurpose())
                .latitude(postMapPointReq.getLatitude())
                .longitude(postMapPointReq.getLongitude())
                .memberId(memberId).build();
    }

    public List<GetMapPointsRes> toGetMapPointsResList(List<GetMapPointDto> mapPointList) {
        return mapPointList.stream().map(
                getMapPointDto -> GetMapPointsRes.builder()
                        .nickname(getMapPointDto.getNickname())
                        .latitude(getMapPointDto.getLatitude())
                        .longitude(getMapPointDto.getLongitude()).build()
        ).collect(Collectors.toList());
    }
}
