package gcu.mp.service.orderPost;

import gcu.mp.domain.member.domin.Member;
import gcu.mp.domain.orderPost.domain.OrderPost;
import gcu.mp.domain.orderPost.repository.OrderPostRepository;
import gcu.mp.domain.orderPost.vo.State;
import gcu.mp.service.member.MemberService;
import gcu.mp.service.orderPost.dto.CreateOrderPostDto;
import gcu.mp.service.orderPost.dto.GetOrderPostDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static gcu.mp.domain.orderPost.vo.Progress.ING;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OrderPostServiceImpl implements OrderPostService {
    private final MemberService memberService;
    private final OrderPostRepository orderPostRepository;

    @Override
    @Transactional
    public void createOrderPost(CreateOrderPostDto createOrderPostDto) {
        Member member = memberService.getMember(createOrderPostDto.getMemberId());
        OrderPost orderPost = OrderPost.builder()
                .content(createOrderPostDto.getContent())
                .title(createOrderPostDto.getTitle())
                .estimated_time(createOrderPostDto.getEstimatedTime())
                .state(State.A)
                .progress(ING)
                .build();
        orderPost.setMember(member);
        orderPostRepository.save(orderPost);
    }

    //todo 댓글 수 가져오기
    @Override
    public List<GetOrderPostDto> getOrderPostList(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, "id");
        List<OrderPost> orderPostList = orderPostRepository.findByState(State.A, pageRequest);
        return orderPostList.stream().map(
                orderPost -> GetOrderPostDto.builder()
                        .estimatedTime(orderPost.getEstimated_time())
                        .progress(orderPost.getProgress().getName())
                        .commentNum(0)
                        .title(orderPost.getTitle()).build()
        ).collect(Collectors.toList());
    }
}