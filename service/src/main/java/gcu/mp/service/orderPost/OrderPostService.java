package gcu.mp.service.orderPost;


import gcu.mp.service.orderPost.dto.*;

import java.util.List;

public interface OrderPostService {
    void createOrderPost(CreateOrderPostDto createOrderPostDto);

    List<GetOrderPostListDto> getOrderPostList(Integer page, Integer size);

    GetOrderPostDetailDto getOrderPostDetail(Long orderPostId);

    List<OrderPostCommentDto> getOrderPostCommentList(Long orderPostId, Integer page, Integer size);
    void createOrderPostDetailComment(CreateOrderPostCommentDto createOrderPostCommentDto);

    boolean existOrderPost(long postId);
}
