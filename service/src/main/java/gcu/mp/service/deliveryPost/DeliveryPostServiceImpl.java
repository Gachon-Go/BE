package gcu.mp.service.deliveryPost;

import gcu.mp.domain.deliveryPost.domain.DeliveryPost;
import gcu.mp.domain.deliveryPost.repository.DeliveryPostRepository;
import gcu.mp.domain.deliveryPost.vo.State;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class DeliveryPostServiceImpl implements DeliveryPostService {
    private final DeliveryPostRepository deliveryPostRepository;

}
