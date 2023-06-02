package gcu.mp.service.orderPost;


import gcu.mp.domain.member.domin.Member;
import gcu.mp.domain.orderPost.domain.OrderPost;
import gcu.mp.domain.orderPost.domain.OrderPostProgress;
import gcu.mp.service.orderPost.dto.*;

import java.util.List;
import java.util.Optional;

public interface OrderPostService {
    void createOrderPost(CreateOrderPostDto createOrderPostDto);

    List<GetOrderPostListDto> getOrderPostList(Integer page, Integer size);

    GetOrderPostDetailDto getOrderPostDetail(Long memberId,Long orderPostId);

    List<OrderPostCommentDto> getOrderPostCommentList(Long orderPostId, Integer page, Integer size);
    void createOrderPostDetailComment(CreateOrderPostCommentDto createOrderPostCommentDto);


    void selectOrderPostCustomer(Long orderPostId, Long commentId);

    List<Member> getOrderPostProgressMemberIdByMemberId(Long memberId);

    Long getOrderPostProgressOrderIdByMemberId(Long memberId);

    void doneSelectOrderPostCustomer(Long memberId, Long orderPostId);

    boolean existOrderPostProgress(Long orderPostId);

    boolean existProgressingOrderPostByMemberId(Long memberId);

    Optional<OrderPostProgress> existProgressingOrderPostProgressByMemberId(Long memberId);

    OrderPost getOrderPost(Long id);

    List<OrderPostProgress> getOrderPostProgressListByPostId(Long id);

    boolean checkMemberPost(Long memberId, Long orderPostId);

    int getOrderPostSize(Long memberId);

    int getOrderProgressPostSize(Long memberId);
}
