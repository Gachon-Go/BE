package gcu.mp.service.orderPost;

import gcu.mp.common.api.BaseResponseStatus;
import gcu.mp.common.exception.BaseException;
import gcu.mp.domain.entity.BaseEntity;
import gcu.mp.domain.member.domin.Member;
import gcu.mp.domain.orderPost.domain.OrderPost;
import gcu.mp.domain.orderPost.domain.OrderPostComment;
import gcu.mp.domain.orderPost.domain.OrderPostProgress;
import gcu.mp.domain.orderPost.repository.OrderPostCommentRepository;
import gcu.mp.domain.orderPost.repository.OrderPostProgressRepository;
import gcu.mp.domain.orderPost.repository.OrderPostRepository;
import gcu.mp.domain.orderPost.vo.ProgressState;
import gcu.mp.domain.orderPost.vo.State;
import gcu.mp.service.member.MemberService;
import gcu.mp.service.notification.Dto.NotificationEventDto;
import gcu.mp.service.notification.NotificationService;
import gcu.mp.service.orderPost.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static gcu.mp.common.api.BaseResponseStatus.*;
import static gcu.mp.domain.orderPost.vo.Progress.ING;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OrderPostServiceImpl implements OrderPostService {
    private final MemberService memberService;
    private final OrderPostRepository orderPostRepository;
    private final OrderPostCommentRepository orderPostCommentRepository;
    private final OrderPostProgressRepository orderPostProgressRepository;
    private final NotificationService notificationService;
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
        OrderPostProgress orderPostProgress = OrderPostProgress.builder()
                .progressState(ProgressState.WAIT)
                .state(State.A).build();
        orderPostProgress.setMember(member);
        orderPostProgress.setOrderPost(orderPost);
        orderPost.setMember(member);
        orderPostRepository.save(orderPost);
    }

    @Override
    public List<GetOrderPostListDto> getOrderPostList(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, "id");
        List<OrderPost> orderPostList = orderPostRepository.findByState(State.A, pageRequest);
        return orderPostList.stream().map(
                orderPost -> GetOrderPostListDto.builder()
                        .orderId(orderPost.getId())
                        .estimatedTime(orderPost.getEstimated_time())
                        .progress(orderPost.getProgress().getName())
                        .commentNum(orderPost.getOrderPostCommentList().size())
                        .title(orderPost.getTitle()).build()
        ).collect(Collectors.toList());
    }

    @Override
    public GetOrderPostDetailDto getOrderPostDetail(Long memberId, Long orderPostId) {
        OrderPost orderPost = getOrderPost(orderPostId);
        return GetOrderPostDetailDto.builder()
                .mine(orderPost.getMember().getId().equals(memberId))
                .writer(orderPost.getMember().getNickname())
                .commentNum(orderPost.getOrderPostCommentList().size())
                .content(orderPost.getContent())
                .estimatedTime(orderPost.getEstimated_time())
                .title(orderPost.getTitle()).build();
    }

    @Override
    public List<OrderPostCommentDto> getOrderPostCommentList(Long orderPostId, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, "id");
        List<OrderPostComment> orderPostCommentList = orderPostCommentRepository.findByOrderPostIdAndState(orderPostId, State.A, pageRequest);
        return orderPostCommentList.stream().map(
                orderPostComment -> OrderPostCommentDto.builder()
                        .commentId(orderPostComment.getId())
                        .commentWriter(orderPostComment.getMember().getNickname())
                        .content(orderPostComment.getContent()).build()
        ).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void createOrderPostDetailComment(CreateOrderPostCommentDto createOrderPostCommentDto) {
        Member member = memberService.getMember(createOrderPostCommentDto.getMemberId());
        OrderPost orderPost = getOrderPost(createOrderPostCommentDto.getOrderPostId());
        OrderPostComment orderPostComment = OrderPostComment.builder()
                .content(createOrderPostCommentDto.getContent())
                .state(State.A).build();
        orderPostComment.setOrderPost(orderPost);
        orderPostComment.setMember(member);
        orderPostCommentRepository.save(orderPostComment);
        NotificationEventDto notificationEventDto = NotificationEventDto.builder()
                .memberId(orderPost.getMember().getId())
                .flag(4)
                .content("내가 작성한 게시물 "+orderPost.getTitle()+"에 새로운 댓글이 작성되었습니다.")
                .build();
        notificationService.notificationEvent(notificationEventDto);
    }

    @Override
    @Transactional
    public void selectOrderPostCustomer(Long orderPostId, Long commentId) {
        OrderPost orderPost = getOrderPost(orderPostId);
        Member member = getOrderPostComment(commentId).getMember();
        OrderPostProgress orderPostProgress = OrderPostProgress.builder()
                .state(State.A)
                .progressState(ProgressState.WAIT).build();
        orderPostProgress.setOrderPost(orderPost);
        orderPostProgress.setMember(member);
        orderPostProgressRepository.save(orderPostProgress);
    }

    @Override
    public List<Member> getOrderPostProgressMemberIdByMemberId(Long memberId) {
        log.info("1");
        Optional<OrderPostProgress> orderPostProgressOptional = orderPostProgressRepository.findByMemberIdAndStateAndProgressState(memberId, State.A, ProgressState.ING);
        log.info("2");
        if (orderPostProgressOptional.isEmpty()) {
            log.info("3");
            return new ArrayList<>();
        }
        else {
            Long orderPostId = orderPostProgressOptional.get().getOrderPost().getId();
            List<OrderPostProgress> orderPostProgressList = orderPostProgressRepository.findByOrderPostIdAndStateAndProgressState(orderPostId, State.A, ProgressState.ING);
            log.info("4");
            return orderPostProgressList.stream().map(
                    OrderPostProgress::getMember
            ).collect(Collectors.toList());
        }
    }

    @Override
    public Long getOrderPostProgressOrderIdByMemberId(Long memberId) {
        Optional<OrderPostProgress> orderPostProgressOptional = orderPostProgressRepository.findByMemberIdAndStateAndProgressState(memberId, State.A, ProgressState.ING);
        return orderPostProgressOptional.map(orderPostProgress -> orderPostProgress.getOrderPost().getId()).orElse(null);
    }

    @Override
    @Transactional
    public void doneSelectOrderPostCustomer(Long memberId, Long orderPostId) {
        Member member = memberService.getMember(memberId);
        List<OrderPostProgress> orderPostProgressList = orderPostProgressRepository.findByOrderPostIdAndStateAndProgressState(orderPostId, State.A, ProgressState.WAIT);
        for (OrderPostProgress orderPostProgress : orderPostProgressList) {
            orderPostProgress.updateProgressState(ProgressState.ING);
        }
        for (OrderPostProgress orderPostProgress : orderPostProgressList) {
            NotificationEventDto notificationEventDto = NotificationEventDto.builder()
                    .memberId(orderPostProgress.getMember().getId())
                    .flag(1)
                    .content(orderPostProgress.getOrderPost().getTitle()+"게시글에서 배달기사로 선택되었습니다.")
                    .build();
            notificationService.notificationEvent(notificationEventDto);
        }
    }

    @Override
    public boolean existOrderPostProgress(Long orderPostId) {
        List<OrderPostProgress> orderPostProgressList = orderPostProgressRepository.findByOrderPostIdAndState(orderPostId, State.A);
        for (OrderPostProgress orderPostProgress : orderPostProgressList) {
            if (orderPostProgress.getProgressState().equals(ProgressState.WAIT) || orderPostProgress.getProgressState().equals(ProgressState.ING)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean existProgressingOrderPostByMemberId(Long memberId) {
        return orderPostRepository.existsByMemberIdAndStateAndProgress(memberId, State.A, ING);
    }

    @Override
    public Optional<OrderPostProgress> existProgressingOrderPostProgressByMemberId(Long memberId) {
        return orderPostProgressRepository.findByMemberIdAndStateAndProgressState(memberId, State.A, ProgressState.ING);
    }

    public OrderPostComment getOrderPostComment(Long commentId) {
        return orderPostCommentRepository.findByIdAndState(commentId, State.A).orElseThrow(() -> new BaseException(NOT_EXIST_COMMENT));
    }

    @Override
    public OrderPost getOrderPost(Long orderPostId) {
        return orderPostRepository.findByIdAndState(orderPostId, State.A).orElseThrow(() -> new BaseException(NOT_EXIST_POST));
    }

    @Override
    public List<OrderPostProgress> getOrderPostProgressListByPostId(Long id) {
        return orderPostProgressRepository.findByOrderPostIdAndStateAndProgressState(id,State.A,ProgressState.ING);
    }
}