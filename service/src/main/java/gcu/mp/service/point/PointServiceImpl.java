package gcu.mp.service.point;

import gcu.mp.domain.member.domin.Member;
import gcu.mp.domain.point.repository.PointHistoryRepository;
import gcu.mp.service.member.MemberService;
import gcu.mp.service.point.dto.GetPointRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PointServiceImpl implements PointService{
    private final MemberService memberService;
    private final PointHistoryRepository pointHistoryRepository;
    @Override
    public GetPointRes getPoint(Long memberId) {
        Member member = memberService.getMember(memberId);
        return GetPointRes.builder()
                .point(member.getPoint()).build();
    }
}
