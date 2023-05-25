package gcu.mp.api.deliveryPost;

import gcu.mp.api.deliveryPost.dto.request.CreateDeliveryPostCommentReq;
import gcu.mp.api.deliveryPost.dto.request.CreateDeliveryPostReq;
import gcu.mp.api.deliveryPost.dto.response.DeliveryPostCommentListRes;
import gcu.mp.api.deliveryPost.dto.response.GetDeliveryPostDetailRes;
import gcu.mp.api.deliveryPost.dto.response.GetDeliveryPostListRes;
import gcu.mp.api.deliveryPost.mapper.DeliveryPostMapper;
import gcu.mp.common.api.BaseResponse;
import gcu.mp.common.api.BaseResponseStatus;
import gcu.mp.common.exception.BaseException;
import gcu.mp.service.deliveryPost.DeliveryPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "배달 게시물")
@RequestMapping(value = "/delivery")
public class DeliveryPostController {
    private final DeliveryPostService deliveryPostService;
    private final DeliveryPostMapper deliveryPostMapper;

    @Operation(summary = "배달 게시물 작성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "성공"),
            @ApiResponse(responseCode = "2004", description = "유효하지 않은 토큰입니다.", content = @Content),
            @ApiResponse(responseCode = "2012", description = "권한이 없는 유저의 접근입니다.", content = @Content),
            @ApiResponse(responseCode = "2103", description = "존재하지 않는 유저입니다.", content = @Content),
            @ApiResponse(responseCode = "4001", description = "서버 오류입니다.", content = @Content)
    })
    @PostMapping("")
    public ResponseEntity<BaseResponse<String>> createDelivery(@RequestBody CreateDeliveryPostReq createDeliveryPostReq) {
        try {
            Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
            Long memberId = Long.parseLong(loggedInUser.getName());
            deliveryPostService.createDeliveryPost(deliveryPostMapper.toCreateDeliveryPostDto(memberId, createDeliveryPostReq));
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(BaseResponseStatus.SUCCESS));
        } catch (BaseException e) {
            return ResponseEntity.status(e.getStatus().getHttpCode()).body(new BaseResponse<>(e.getStatus()));
        }
    }

    @Operation(summary = "배달 게시물 리스트 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "성공"),
            @ApiResponse(responseCode = "2004", description = "유효하지 않은 토큰입니다.", content = @Content),
            @ApiResponse(responseCode = "2012", description = "권한이 없는 유저의 접근입니다.", content = @Content),
            @ApiResponse(responseCode = "4001", description = "서버 오류입니다.", content = @Content)
    })
    @GetMapping("")
    public ResponseEntity<BaseResponse<List<GetDeliveryPostListRes>>> getDeliveryPostList(@Parameter(name = "page", description = " 페이지 0이상", in = ParameterIn.QUERY) @RequestParam(required = false) Integer page,
                                                                                       @Parameter(name = "size", description = " 페이지 사이즈  1이상", in = ParameterIn.QUERY) @RequestParam(required = false) Integer size) {
        try {
            List<GetDeliveryPostListRes> getDeliveryPostListResList = deliveryPostMapper.toGetDeliveryPostListResList(deliveryPostService.getDeliveryPostList(page, size));
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(getDeliveryPostListResList));
        } catch (BaseException e) {
            return ResponseEntity.status(e.getStatus().getHttpCode()).body(new BaseResponse<>(e.getStatus()));
        }
    }

    @Operation(summary = "배달 게시물 상세 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "성공"),
            @ApiResponse(responseCode = "2004", description = "유효하지 않은 토큰입니다.", content = @Content),
            @ApiResponse(responseCode = "2012", description = "권한이 없는 유저의 접근입니다.", content = @Content),
            @ApiResponse(responseCode = "4001", description = "서버 오류입니다.", content = @Content)
    })
    @GetMapping("/{deliveryPostId}")
    public ResponseEntity<BaseResponse<GetDeliveryPostDetailRes>> getDeliveryPostDetail(@PathVariable Long deliveryPostId) {
        try {
            Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
            Long memberId = Long.parseLong(loggedInUser.getName());
            GetDeliveryPostDetailRes getDeliveryPostDetailRes = deliveryPostMapper.toGetDeliveryPostDetailRes(deliveryPostService.getDeliveryPostDetail(memberId,deliveryPostId));
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(getDeliveryPostDetailRes));
        } catch (BaseException e) {
            return ResponseEntity.status(e.getStatus().getHttpCode()).body(new BaseResponse<>(e.getStatus()));
        }
    }

    @Operation(summary = "배달 게시물 상세 댓글 작성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "성공"),
            @ApiResponse(responseCode = "2004", description = "유효하지 않은 토큰입니다.", content = @Content),
            @ApiResponse(responseCode = "2012", description = "권한이 없는 유저의 접근입니다.", content = @Content),
            @ApiResponse(responseCode = "4001", description = "서버 오류입니다.", content = @Content)
    })
    @PostMapping("/{deliveryPostId}/comment")
    public ResponseEntity<BaseResponse<String>> createDeliveryPostDetailComment(@PathVariable Long deliveryPostId,
                                                                             @RequestBody CreateDeliveryPostCommentReq createDeliveryPostCommentReq) {
        try {
            Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
            Long memberId = Long.parseLong(loggedInUser.getName());
            deliveryPostService.createDeliveryPostDetailComment(deliveryPostMapper.toCreateDeliveryPostCommentDto(memberId, deliveryPostId, createDeliveryPostCommentReq));
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(BaseResponseStatus.SUCCESS));
        } catch (BaseException e) {
            return ResponseEntity.status(e.getStatus().getHttpCode()).body(new BaseResponse<>(e.getStatus()));
        }
    }

    @Operation(summary = "배달 게시물 상세 댓글 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "성공"),
            @ApiResponse(responseCode = "2004", description = "유효하지 않은 토큰입니다.", content = @Content),
            @ApiResponse(responseCode = "2012", description = "권한이 없는 유저의 접근입니다.", content = @Content),
            @ApiResponse(responseCode = "4001", description = "서버 오류입니다.", content = @Content)
    })
    @GetMapping("/{deliveryPostId}/comment")
    public ResponseEntity<BaseResponse<List<DeliveryPostCommentListRes>>> getDeliveryPostDetailComment(@Parameter(name = "page", description = " 페이지 0이상", in = ParameterIn.QUERY) @RequestParam(required = false) Integer page,
                                                                                                    @Parameter(name = "size", description = " 페이지 사이즈  1이상", in = ParameterIn.QUERY) @RequestParam(required = false) Integer size,
                                                                                                    @PathVariable Long deliveryPostId) {
        try {
            List<DeliveryPostCommentListRes> deliveryPostCommentListResList = deliveryPostMapper.toDeliveryPostCommentListResList(deliveryPostService.getDeliveryPostCommentList(deliveryPostId, page, size));
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(deliveryPostCommentListResList));
        } catch (BaseException e) {
            return ResponseEntity.status(e.getStatus().getHttpCode()).body(new BaseResponse<>(e.getStatus()));
        }
    }

    @Operation(summary = "배달 게시물 고객 선택")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "성공"),
            @ApiResponse(responseCode = "2004", description = "유효하지 않은 토큰입니다.", content = @Content),
            @ApiResponse(responseCode = "2012", description = "권한이 없는 유저의 접근입니다.", content = @Content),
            @ApiResponse(responseCode = "4001", description = "서버 오류입니다.", content = @Content)
    })
    @PostMapping("/{deliveryPostId}/comment/{commentId}/select")
    public ResponseEntity<BaseResponse<String>> selectDeliveryPostCustomer(@PathVariable Long deliveryPostId, @PathVariable Long commentId) {
        try {
            if(deliveryPostService.existDeliveryPostProgress(deliveryPostId)){
                return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(BaseResponseStatus.EXISTS_PROGRESS));
            }
            deliveryPostService.selectDeliveryPostCustomer(deliveryPostId, commentId);
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(BaseResponseStatus.SUCCESS));
        } catch (BaseException e) {
            return ResponseEntity.status(e.getStatus().getHttpCode()).body(new BaseResponse<>(e.getStatus()));
        }
    }
    @Operation(summary = "배달 게시물 모집완료")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "성공"),
            @ApiResponse(responseCode = "2004", description = "유효하지 않은 토큰입니다.", content = @Content),
            @ApiResponse(responseCode = "2012", description = "권한이 없는 유저의 접근입니다.", content = @Content),
            @ApiResponse(responseCode = "4001", description = "서버 오류입니다.", content = @Content)
    })
    @PostMapping("/{deliveryPostId}/done")
    public ResponseEntity<BaseResponse<String>> doneSelectDeliveryPostCustomer(@PathVariable Long deliveryPostId) {
        try {
            Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
            Long memberId = Long.parseLong(loggedInUser.getName());
            deliveryPostService.doneSelectDeliveryPostCustomer(memberId,deliveryPostId);
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(BaseResponseStatus.SUCCESS));
        } catch (BaseException e) {
            return ResponseEntity.status(e.getStatus().getHttpCode()).body(new BaseResponse<>(e.getStatus()));
        }
    }
}
