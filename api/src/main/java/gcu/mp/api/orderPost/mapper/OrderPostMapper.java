package gcu.mp.api.orderPost.mapper;

import gcu.mp.api.orderPost.dto.request.CreateOrderPostCommentReq;
import gcu.mp.api.orderPost.dto.request.CreateOrderPostReq;
import gcu.mp.api.orderPost.dto.response.GetOrderPostDetailRes;
import gcu.mp.api.orderPost.dto.response.GetOrderPostListRes;
import gcu.mp.api.orderPost.dto.response.OrderPostCommentListRes;
import gcu.mp.service.orderPost.dto.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderPostMapper {
    public CreateOrderPostDto toCreateOrderPostDto(Long memberId, CreateOrderPostReq createOrderPostReq) {
        return CreateOrderPostDto.builder()
                .estimatedTime(createOrderPostReq.getEstimatedTime())
                .title(createOrderPostReq.getTitle())
                .content(createOrderPostReq.getContent())
                .memberId(memberId).build();
    }

    public CreateOrderPostCommentDto toCreateOrderPostCommentDto(Long memberId, Long orderPostId, CreateOrderPostCommentReq createOrderPostCommentReq) {
        return CreateOrderPostCommentDto.builder()
                .memberId(memberId)
                .orderPostId(orderPostId)
                .content(createOrderPostCommentReq.getContent())
                .build();
    }

    public List<GetOrderPostListRes> toGetOrderPostListResList(List<GetOrderPostListDto> orderPostList) {
        return orderPostList.stream().map(
                getOrderPostListDto -> GetOrderPostListRes.builder()
                        .orderId(getOrderPostListDto.getOrderId())
                        .commentNum(getOrderPostListDto.getCommentNum())
                        .progress(getOrderPostListDto.getProgress())
                        .estimatedTime(getOrderPostListDto.getEstimatedTime())
                        .title(getOrderPostListDto.getTitle())
                        .build()
        ).collect(Collectors.toList());
    }

    public GetOrderPostDetailRes toGetOrderPostDetailRes(GetOrderPostDetailDto orderPostDetail) {
        return GetOrderPostDetailRes.builder()
                .mine(orderPostDetail.isMine())
                .writer(orderPostDetail.getWriter())
                .title(orderPostDetail.getTitle())
                .content(orderPostDetail.getContent())
                .writerImage(orderPostDetail.getWriterImage())
                .estimatedTime(orderPostDetail.getEstimatedTime())
                .commentNum(orderPostDetail.getCommentNum())
                .build();
    }

    public List<OrderPostCommentListRes> toOrderPostCommentListResList(List<OrderPostCommentDto> orderPostCommentList) {
        return orderPostCommentList.stream().map(
                orderPostCommentDto -> OrderPostCommentListRes.builder()
                        .commentWriterImage(orderPostCommentDto.getCommentWriterImage())
                        .isAccept(orderPostCommentDto.getIsAccept())
                        .commentId(orderPostCommentDto.getCommentId())
                        .commentWriter(orderPostCommentDto.getCommentWriter())
                        .content(orderPostCommentDto.getContent())
                        .build()
        ).collect(Collectors.toList());
    }
}
