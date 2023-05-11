package gcu.mp.service.pay;

import gcu.mp.domain.member.domin.Member;
import gcu.mp.domain.pay.domain.PayHistory;
import gcu.mp.domain.pay.repository.PayHistoryRepository;
import gcu.mp.domain.pay.vo.State;
import gcu.mp.service.member.MemberService;
import gcu.mp.service.pay.dto.PaySuccessDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PayServiceImpl implements PayService {
    private final PayHistoryRepository payHistoryRepository;
    private final MemberService memberService;
    @Transactional
    @Override
    public void paySuccess(PaySuccessDto paySuccessDto) {
        Member member = memberService.getMember(paySuccessDto.getMemberId());
        PayHistory payHistory = PayHistory.builder()
                .tid(paySuccessDto.getTid())
                .state(State.A)
                .totalAmount(paySuccessDto.getTotalAmount()).build();
        member.addPoint(paySuccessDto.getTotalAmount());
        payHistory.setMember(member);
        payHistoryRepository.save(payHistory);
    }
}
