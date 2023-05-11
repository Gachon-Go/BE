package gcu.mp.service.point;

import gcu.mp.service.point.dto.GetPointRes;

public interface PointService {
    GetPointRes getPoint(Long memberId);
}
