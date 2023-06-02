package gcu.mp.service.deliveryPost;

import gcu.mp.domain.deliveryPost.domain.DeliveryPost;
import gcu.mp.domain.deliveryPost.domain.DeliveryPostProgress;
import gcu.mp.domain.member.domin.Member;
import gcu.mp.service.deliveryPost.dto.*;

import java.util.List;
import java.util.Optional;

public interface DeliveryPostService {

    List<Member> getDeliveryPostProgressMemberIdByMemberId(Long memberId);

    Long getDeliveryPostProgressDeliveryIdByMemberId(Long memberId);

    void createDeliveryPost(CreateDeliveryPostDto createDeliveryPostDto);

    List<GetDeliveryPostListDto> getDeliveryPostList(Integer page, Integer size);

    GetDeliveryPostDetailDto getDeliveryPostDetail(Long memberId, Long deliveryPostId);

    List<DeliveryPostCommentDto> getDeliveryPostCommentList(Long deliveryPostId, Integer page, Integer size);

    void createDeliveryPostDetailComment(CreateDeliveryPostCommentDto createDeliveryPostCommentDto);


    void selectDeliveryPostCustomer(Long deliveryPostId, Long commentId);


    void doneSelectDeliveryPostCustomer(Long memberId, Long DeliveryPostId);

    boolean existDeliveryPostProgress(Long commentId);

    boolean existProgressingDeliveryPostByMemberId(Long memberId);

    Optional<DeliveryPostProgress> existProgressingDeliveryPostProgressByMemberId(Long memberId);

    DeliveryPost getDeliveryPost(Long id);

    List<DeliveryPostProgress> getDeliveryPostProgressListByPostId(Long id);

    boolean checkMemberPost(Long memberId, Long deliveryPostId);

    int getDeliveryPostSize(Long memberId);

    int getDeliveryProgressPostSize(Long memberId);
}
