package gcu.mp.service.orderPost;


import gcu.mp.service.orderPost.dto.CreateOrderPostDto;
import gcu.mp.service.orderPost.dto.GetOrderPostDto;

import java.util.List;

public interface OrderPostService {
    void createOrderPost(CreateOrderPostDto createOrderPostDto);

    List<GetOrderPostDto> getOrderPostList(Integer page, Integer size);
}
