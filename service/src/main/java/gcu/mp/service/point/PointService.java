package gcu.mp.service.point;

import gcu.mp.service.point.dto.GetPointDto;
import gcu.mp.service.point.dto.PaysuccessPointDto;
import gcu.mp.service.point.dto.PointHistoryDto;

import java.util.List;

public interface PointService {
    GetPointDto getPoint(Long memberId);

    void paySuccess(PaysuccessPointDto paysuccessPointDto);

    List<PointHistoryDto> getPointHistory(Long memberId, int page, int size);
}
