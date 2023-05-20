package gcu.mp.api.point.mapper;

import gcu.mp.api.point.dto.response.GetPointRes;
import gcu.mp.api.point.dto.response.PointHistoryListRes;
import gcu.mp.service.point.dto.GetPointDto;
import gcu.mp.service.point.dto.PointHistoryDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PointMapper {
    public GetPointRes toGetPointRes(GetPointDto getPointDto) {
        return GetPointRes.builder().
                point(getPointDto.getPoint())
                .build();
    }

    public List<PointHistoryListRes> toPointHistoryListResList(List<PointHistoryDto> pointHistoryDtoList) {
        return pointHistoryDtoList.stream().map(
                pointHistoryDto -> PointHistoryListRes.builder()
                        .time(pointHistoryDto.getTime())
                        .content(pointHistoryDto.getContent())
                        .point(pointHistoryDto.getPoint())
                        .build()
        ).collect(Collectors.toList());
    }
}
