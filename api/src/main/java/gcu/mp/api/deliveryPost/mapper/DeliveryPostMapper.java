package gcu.mp.api.deliveryPost.mapper;

import gcu.mp.api.deliveryPost.dto.request.CreateDeliveryPostCommentReq;
import gcu.mp.api.deliveryPost.dto.request.CreateDeliveryPostReq;
import gcu.mp.api.deliveryPost.dto.response.DeliveryPostCommentListRes;
import gcu.mp.api.deliveryPost.dto.response.GetDeliveryPostDetailRes;
import gcu.mp.api.deliveryPost.dto.response.GetDeliveryPostListRes;
import gcu.mp.service.deliveryPost.dto.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DeliveryPostMapper {
    public CreateDeliveryPostDto toCreateDeliveryPostDto(Long memberId, CreateDeliveryPostReq createDeliveryPostReq) {
        return CreateDeliveryPostDto.builder()
                .estimatedTime(createDeliveryPostReq.getEstimatedTime())
                .title(createDeliveryPostReq.getTitle())
                .content(createDeliveryPostReq.getContent())
                .memberId(memberId).build();
    }

    public CreateDeliveryPostCommentDto toCreateDeliveryPostCommentDto(Long memberId, Long deliveryPostId, CreateDeliveryPostCommentReq createDeliveryPostCommentReq) {
        return CreateDeliveryPostCommentDto.builder()
                .memberId(memberId)
                .deliveryPostId(deliveryPostId)
                .content(createDeliveryPostCommentReq.getContent())
                .build();
    }

    public List<GetDeliveryPostListRes> toGetDeliveryPostListResList(List<GetDeliveryPostListDto> deliveryPostList) {
        return deliveryPostList.stream().map(
                getDeliveryPostListDto -> GetDeliveryPostListRes.builder()
                        .deliveryId(getDeliveryPostListDto.getDeliveryPostId())
                        .commentNum(getDeliveryPostListDto.getCommentNum())
                        .progress(getDeliveryPostListDto.getProgress())
                        .estimatedTime(getDeliveryPostListDto.getEstimatedTime())
                        .title(getDeliveryPostListDto.getTitle())
                        .build()
        ).collect(Collectors.toList());
    }

    public GetDeliveryPostDetailRes toGetDeliveryPostDetailRes(GetDeliveryPostDetailDto deliveryPostDetail) {
        return GetDeliveryPostDetailRes.builder()
                .mine(deliveryPostDetail.isMine())
                .writer(deliveryPostDetail.getWriter())
                .title(deliveryPostDetail.getTitle())
                .writerImage(deliveryPostDetail.getWriterImage())
                .content(deliveryPostDetail.getContent())
                .estimatedTime(deliveryPostDetail.getEstimatedTime())
                .commentNum(deliveryPostDetail.getCommentNum())
                .build();
    }

    public List<DeliveryPostCommentListRes> toDeliveryPostCommentListResList(List<DeliveryPostCommentDto> deliveryPostCommentList) {
        return deliveryPostCommentList.stream().map(
                deliveryPostCommentDto -> DeliveryPostCommentListRes.builder()
                        .commentWriterImage(deliveryPostCommentDto.getCommentWriterImage())
                        .commentId(deliveryPostCommentDto.getCommentId())
                        .commentWriter(deliveryPostCommentDto.getCommentWriter())
                        .content(deliveryPostCommentDto.getContent())
                        .build()
        ).collect(Collectors.toList());
    }
}
