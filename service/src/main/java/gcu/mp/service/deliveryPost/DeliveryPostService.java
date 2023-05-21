package gcu.mp.service.deliveryPost;

import gcu.mp.domain.member.domin.Member;
import gcu.mp.service.deliveryPost.dto.*;

import java.util.List;

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

    boolean existDeliveryPostProgress(Long deliveryPostId);
}
