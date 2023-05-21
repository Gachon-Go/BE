package gcu.mp.service.deliveryPost;

import gcu.mp.common.exception.BaseException;
import gcu.mp.domain.deliveryPost.domain.DeliveryPost;
import gcu.mp.domain.deliveryPost.domain.DeliveryPostComment;
import gcu.mp.domain.deliveryPost.domain.DeliveryPostProgress;
import gcu.mp.domain.deliveryPost.repository.DeliveryPostCommentRepository;
import gcu.mp.domain.deliveryPost.repository.DeliveryPostProgressRepository;
import gcu.mp.domain.deliveryPost.repository.DeliveryPostRepository;
import gcu.mp.domain.deliveryPost.vo.ProgressState;
import gcu.mp.domain.deliveryPost.vo.State;
import gcu.mp.domain.entity.BaseEntity;
import gcu.mp.domain.member.domin.Member;
import gcu.mp.service.deliveryPost.dto.*;
import gcu.mp.service.member.MemberService;
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

import static gcu.mp.common.api.BaseResponseStatus.NOT_EXIST_COMMENT;
import static gcu.mp.common.api.BaseResponseStatus.NOT_EXIST_POST;
import static gcu.mp.domain.deliveryPost.vo.Progress.ING;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class DeliveryPostServiceImpl implements DeliveryPostService {
    private final DeliveryPostRepository deliveryPostRepository;
    private final DeliveryPostProgressRepository deliveryPostProgressRepository;
    private final MemberService memberService;
    private final DeliveryPostCommentRepository deliveryPostCommentRepository;

    @Override
    public List<Member> getDeliveryPostProgressMemberIdByMemberId(Long memberId) {
        Optional<DeliveryPostProgress> deliveryPostProgressOptional = deliveryPostProgressRepository.findByMemberIdAndStateAndProgressState(memberId, State.A, ProgressState.ING);
        if (deliveryPostProgressOptional.isEmpty())
            return new ArrayList<>();
        else {
            Long orderPostId = deliveryPostProgressOptional.get().getDeliveryPost().getId();
            List<DeliveryPostProgress> deliveryPostProgressList = deliveryPostProgressRepository.findByDeliveryPostIdAndStateAndProgressState(orderPostId, State.A, ProgressState.ING);
            return deliveryPostProgressList.stream().map(
                    DeliveryPostProgress::getMember
            ).collect(Collectors.toList());
        }
    }

    @Override
    public Long getDeliveryPostProgressDeliveryIdByMemberId(Long memberId) {
        Optional<DeliveryPostProgress> deliveryPostProgressOptional = deliveryPostProgressRepository.findByMemberIdAndStateAndProgressState(memberId, State.A, ProgressState.ING);
        return deliveryPostProgressOptional.map(BaseEntity::getId).orElse(null);
    }

    @Override
    @Transactional
    public void createDeliveryPost(CreateDeliveryPostDto createDeliveryPostDto) {
        Member member = memberService.getMember(createDeliveryPostDto.getMemberId());
        DeliveryPost deliveryPost = DeliveryPost.builder()
                .content(createDeliveryPostDto.getContent())
                .title(createDeliveryPostDto.getTitle())
                .estimated_time(createDeliveryPostDto.getEstimatedTime())
                .state(State.A)
                .progress(ING)
                .build();
        deliveryPost.setMember(member);
        deliveryPostRepository.save(deliveryPost);
    }

    @Override
    public List<GetDeliveryPostListDto> getDeliveryPostList(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, "id");
        List<DeliveryPost> deliveryPostList = deliveryPostRepository.findByState(State.A, pageRequest);
        return deliveryPostList.stream().map(
                deliveryPost -> GetDeliveryPostListDto.builder()
                        .deliveryPostId(deliveryPost.getId())
                        .estimatedTime(deliveryPost.getEstimated_time())
                        .progress(deliveryPost.getProgress().getName())
                        .commentNum(deliveryPost.getDeliveryPostCommentList().size())
                        .title(deliveryPost.getTitle()).build()
        ).collect(Collectors.toList());
    }

    @Override
    public GetDeliveryPostDetailDto getDeliveryPostDetail(Long memberId, Long deliveryPostId) {
        DeliveryPost deliveryPost = getDeliveryPost(deliveryPostId);
        return GetDeliveryPostDetailDto.builder()
                .mine(deliveryPost.getMember().getId().equals(memberId))
                .writer(deliveryPost.getMember().getNickname())
                .commentNum(deliveryPost.getDeliveryPostCommentList().size())
                .content(deliveryPost.getContent())
                .estimatedTime(deliveryPost.getEstimated_time())
                .title(deliveryPost.getTitle()).build();
    }

    @Override
    public List<DeliveryPostCommentDto> getDeliveryPostCommentList(Long deliveryPostId, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, "id");
        List<DeliveryPostComment> deliveryPostCommentList = deliveryPostCommentRepository.findByDeliveryPostIdAndState(deliveryPostId, State.A, pageRequest);
        return deliveryPostCommentList.stream().map(
                deliveryPostComment -> DeliveryPostCommentDto.builder()
                        .commentId(deliveryPostComment.getId())
                        .commentWriter(deliveryPostComment.getMember().getNickname())
                        .content(deliveryPostComment.getContent()).build()
        ).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void createDeliveryPostDetailComment(CreateDeliveryPostCommentDto createDeliveryPostCommentDto) {
        Member member = memberService.getMember(createDeliveryPostCommentDto.getMemberId());
        DeliveryPost deliveryPost = getDeliveryPost(createDeliveryPostCommentDto.getDeliveryPostId());
        DeliveryPostComment deliveryPostComment = DeliveryPostComment.builder()
                .content(createDeliveryPostCommentDto.getContent())
                .state(State.A).build();
        deliveryPostComment.setDeliveryPost(deliveryPost);
        deliveryPostComment.setMember(member);
        deliveryPostCommentRepository.save(deliveryPostComment);
    }

    @Override
    @Transactional
    public void selectDeliveryPostCustomer(Long deliveryPostId, Long commentId) {
        DeliveryPost deliveryPost = getDeliveryPost(deliveryPostId);
        Member member = getDeliveryPostComment(commentId).getMember();
        DeliveryPostProgress deliveryPostProgress = DeliveryPostProgress.builder()
                .state(State.A)
                .progressState(ProgressState.WAIT).build();
        deliveryPostProgress.setDeliveryPost(deliveryPost);
        deliveryPostProgress.setMember(member);
        deliveryPostProgressRepository.save(deliveryPostProgress);
    }

    @Override
    @Transactional
    public void doneSelectDeliveryPostCustomer(Long memberId, Long deliveryPostId) {
        Member member = memberService.getMember(memberId);
        List<DeliveryPostProgress> deliveryPostProgressList = deliveryPostProgressRepository.findByDeliveryPostIdAndStateAndProgressState(deliveryPostId, State.A, ProgressState.WAIT);
        for (DeliveryPostProgress deliveryPostProgress : deliveryPostProgressList) {
            deliveryPostProgress.updateProgressState(ProgressState.ING);
        }
    }

    @Override
    public boolean existDeliveryPostProgress(Long deliveryPostId) {
        List<DeliveryPostProgress> deliveryPostProgressList = deliveryPostProgressRepository.findByDeliveryPostIdAndState(deliveryPostId, State.A);
        for (DeliveryPostProgress deliveryPostProgress : deliveryPostProgressList) {
            if (deliveryPostProgress.getProgressState().equals(ProgressState.WAIT) || deliveryPostProgress.getProgressState().equals(ProgressState.ING)) {
                return true;
            }
        }
        return false;
    }

    public DeliveryPostComment getDeliveryPostComment(Long commentId) {
        return deliveryPostCommentRepository.findByIdAndState(commentId, State.A).orElseThrow(() -> new BaseException(NOT_EXIST_COMMENT));
    }

    public DeliveryPost getDeliveryPost(Long deliveryPostId) {
        return deliveryPostRepository.findByIdAndState(deliveryPostId, State.A).orElseThrow(() -> new BaseException(NOT_EXIST_POST));
    }
}
