package gcu.mp.service.point;

import gcu.mp.domain.member.domin.Member;
import gcu.mp.domain.point.domin.PointHistory;
import gcu.mp.domain.point.repository.PointHistoryRepository;
import gcu.mp.domain.point.vo.State;
import gcu.mp.service.member.MemberService;
import gcu.mp.service.point.dto.GetPointDto;
import gcu.mp.service.point.dto.PaysuccessPointDto;
import gcu.mp.service.point.dto.PointHistoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PointServiceImpl implements PointService {
    private final MemberService memberService;
    private final PointHistoryRepository pointHistoryRepository;

    @Override
    public GetPointDto getPoint(Long memberId) {
        Member member = memberService.getMember(memberId);
        return GetPointDto.builder()
                .point(member.getPoint()).build();
    }

    @Transactional
    @Override
    public void paySuccess(PaysuccessPointDto paysuccessPointDto) {
        Member member = memberService.getMember(paysuccessPointDto.getMemberId());
        PointHistory pointHistory = PointHistory.builder()
                .point(paysuccessPointDto.getPoint())
                .state(State.A)
                .flag("+")
                .content("포인트 충전")
                .build();
        pointHistory.setMember(member);
        pointHistoryRepository.save(pointHistory);
    }

    @Override
    public List<PointHistoryDto> getPointHistory(Long memberId, int page, int size) {
        Member member = memberService.getMember(memberId);
        PageRequest pageRequest = PageRequest.of(page, size);
        List<PointHistory> pointHistoryList = pointHistoryRepository.findByMemberIdAndState(memberId, State.A, pageRequest);
        return pointHistoryList.stream()
                .map(pointHistory -> PointHistoryDto.builder()
                        .content(pointHistory.getContent())
                        .point(pointHistory.getFlag() + pointHistory.getPoint() + "P")
                        .time(pointHistory.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
                        .build()).collect(Collectors.toList());
    }
}
