package gcu.mp.service.pay;

import gcu.mp.domain.pay.repository.PayHistoryRepository;
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

}
