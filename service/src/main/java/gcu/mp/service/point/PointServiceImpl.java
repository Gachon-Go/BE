package gcu.mp.service.point;

import gcu.mp.domain.member.domin.Member;
import gcu.mp.domain.point.domin.PointHistory;
import gcu.mp.domain.point.repository.PointHistoryRepository;
import gcu.mp.domain.point.vo.State;
import gcu.mp.service.member.MemberService;
import gcu.mp.service.point.dto.GetPointRes;
import gcu.mp.service.point.dto.PaysuccessPointDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PointServiceImpl implements PointService {
    private final MemberService memberService;
    private final PointHistoryRepository pointHistoryRepository;

    @Override
    public GetPointRes getPoint(Long memberId) {
        Member member = memberService.getMember(memberId);
        return GetPointRes.builder()
                .point(member.getPoint()).build();
    }

    @Transactional
    @Override
    public void paySuccess(PaysuccessPointDto paysuccessPointDto) {
        Member member = memberService.getMember(paysuccessPointDto.getMemberId());
        log.info("dddddd");
        PointHistory pointHistory = PointHistory.builder()
                .point(paysuccessPointDto.getPoint())
                .state(State.A)
                .flag("포인트 충전")
                .build();
        pointHistory.setMember(member);
        pointHistoryRepository.save(pointHistory);
    }
}
