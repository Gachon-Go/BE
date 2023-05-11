package gcu.mp.service.point;

import gcu.mp.service.point.dto.GetPointRes;
import gcu.mp.service.point.dto.PaysuccessPointDto;

public interface PointService {
    GetPointRes getPoint(Long memberId);

    void paySuccess(PaysuccessPointDto paysuccessPointDto);
}
