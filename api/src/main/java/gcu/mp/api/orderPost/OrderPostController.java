package gcu.mp.api.orderPost;

import gcu.mp.api.orderPost.dto.request.CreateOrderPostReq;
import gcu.mp.api.orderPost.mapper.OrderPostMapper;
import gcu.mp.common.api.BaseResponse;
import gcu.mp.common.api.BaseResponseStatus;
import gcu.mp.service.orderPost.OrderPostService;
import gcu.mp.service.orderPost.dto.GetOrderPostDetailDto;
import gcu.mp.service.orderPost.dto.GetOrderPostListDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Tag(name = "주문 게시물")
@RequestMapping(value = "/order")
public class OrderPostController {
    private final OrderPostService orderPostService;
    private final OrderPostMapper orderPostMapper;
    @Operation(summary = "주문 게시물 작성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "성공", content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "2004", description = "유효하지 않은 토큰입니다.", content = @Content),
            @ApiResponse(responseCode = "2012", description = "권한이 없는 유저의 접근입니다.", content = @Content),
            @ApiResponse(responseCode = "2103", description = "존재하지 않는 유저입니다.", content = @Content),
            @ApiResponse(responseCode = "4001", description = "서버 오류입니다.", content = @Content)
    })
    @PostMapping("")
    public ResponseEntity<BaseResponse<String>> createOrderPost(@RequestBody CreateOrderPostReq createOrderPostReq) {
        try {
            Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
            Long memberId = Long.parseLong(loggedInUser.getName());
            orderPostService.createOrderPost(orderPostMapper.toCreateOrderPostDto(memberId,createOrderPostReq));
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(BaseResponseStatus.SUCCESS));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse<>(BaseResponseStatus.SERVER_ERROR));
        }
    }
    @Operation(summary = "주문 게시물 리스트 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "성공", content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "2004", description = "유효하지 않은 토큰입니다.", content = @Content),
            @ApiResponse(responseCode = "2012", description = "권한이 없는 유저의 접근입니다.", content = @Content),
            @ApiResponse(responseCode = "4001", description = "서버 오류입니다.", content = @Content)
    })
    @GetMapping("")
    public ResponseEntity<BaseResponse<List<GetOrderPostListDto>>> getOrderPostList(@Parameter(name = "page", description = " 페이지 0이상", in = ParameterIn.QUERY) @RequestParam(required = false) Integer page,
                                                                                    @Parameter(name = "size", description = " 페이지 사이즈  1이상", in = ParameterIn.QUERY) @RequestParam(required = false) Integer size) {
        try {
            List<GetOrderPostListDto> getOrderPostDtoList = orderPostService.getOrderPostList(page,size);
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(getOrderPostDtoList));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse<>(BaseResponseStatus.SERVER_ERROR));
        }
    }
    @Operation(summary = "주문 게시물 상세 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "성공", content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "2004", description = "유효하지 않은 토큰입니다.", content = @Content),
            @ApiResponse(responseCode = "2012", description = "권한이 없는 유저의 접근입니다.", content = @Content),
            @ApiResponse(responseCode = "4001", description = "서버 오류입니다.", content = @Content)
    })
    @GetMapping("/{orderPostId}")
    public ResponseEntity<BaseResponse<GetOrderPostDetailDto>> getOrderPostDetail(@PathVariable Long orderPostId) {
        try {
            GetOrderPostDetailDto getOrderPostDetailDto = orderPostService.getOrderPostDetail(orderPostId);
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(getOrderPostDetailDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse<>(BaseResponseStatus.SERVER_ERROR));
        }
    }
}
