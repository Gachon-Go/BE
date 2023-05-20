package gcu.mp.service.orderPost;


import gcu.mp.service.orderPost.dto.CreateOrderPostDto;
import gcu.mp.service.orderPost.dto.GetOrderPostDetailDto;
import gcu.mp.service.orderPost.dto.GetOrderPostListDto;

import java.util.List;

public interface OrderPostService {
    void createOrderPost(CreateOrderPostDto createOrderPostDto);

    List<GetOrderPostListDto> getOrderPostList(Integer page, Integer size);

    GetOrderPostDetailDto getOrderPostDetail(Long orderPostId);
}
