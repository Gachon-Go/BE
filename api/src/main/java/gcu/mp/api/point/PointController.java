package gcu.mp.api.point;

import gcu.mp.api.point.dto.request.GetPointTransactionIdReq;
import gcu.mp.api.point.dto.response.GetPointRes;
import gcu.mp.api.point.dto.response.GetPointTransactionIdRes;
import gcu.mp.api.point.dto.response.PointHistoryListRes;
import gcu.mp.api.point.mapper.PointMapper;
import gcu.mp.common.exception.BaseException;
import gcu.mp.service.point.dto.GetPointDto;
import gcu.mp.common.api.BaseResponse;
import gcu.mp.common.api.BaseResponseStatus;
import gcu.mp.service.point.PointService;
import gcu.mp.service.point.dto.PointHistoryDto;
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
@Tag(name = "포인트")
@RequestMapping(value = "/point")
public class PointController {
    private final PointService pointService;
    private final PointMapper pointMapper;

    @Operation(summary = "포인트 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "성공"),
            @ApiResponse(responseCode = "2004", description = "유효하지 않은 토큰입니다.", content = @Content),
            @ApiResponse(responseCode = "2012", description = "권한이 없는 유저의 접근입니다.", content = @Content),
            @ApiResponse(responseCode = "2103", description = "존재하지 않는 유저입니다.", content = @Content),
            @ApiResponse(responseCode = "4001", description = "서버 오류입니다.", content = @Content)
    })
    @GetMapping("")
    public ResponseEntity<BaseResponse<GetPointRes>> getPoint() {
        try {
            Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
            Long memberId = Long.parseLong(loggedInUser.getName());
            GetPointRes getPointRes = pointMapper.toGetPointRes(pointService.getPoint(memberId));
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(getPointRes));

        } catch (BaseException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse<>(e.getStatus()));
        }
    }

    @Operation(summary = "포인트 내역 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "성공"),
            @ApiResponse(responseCode = "2004", description = "유효하지 않은 토큰입니다.", content = @Content),
            @ApiResponse(responseCode = "2012", description = "권한이 없는 유저의 접근입니다.", content = @Content),
            @ApiResponse(responseCode = "2103", description = "존재하지 않는 유저입니다.", content = @Content),
            @ApiResponse(responseCode = "4001", description = "서버 오류입니다.", content = @Content)
    })
    @GetMapping("/history")
    public ResponseEntity<BaseResponse<List<PointHistoryListRes>>> getPointHistory(@Parameter(name = "page", description = " 페이지 0이상", in = ParameterIn.QUERY) @RequestParam(required = false) Integer page,
                                                                                   @Parameter(name = "size", description = " 페이지 사이즈  1이상", in = ParameterIn.QUERY) @RequestParam(required = false) Integer size) {
        try {
            Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
            Long memberId = Long.parseLong(loggedInUser.getName());
            List<PointHistoryListRes> pointHistoryListResList = pointMapper.toPointHistoryListResList(pointService.getPointHistory(memberId, page, size));
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(pointHistoryListResList));

        } catch (BaseException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse<>(e.getStatus()));
        }
    }

    @Operation(summary = "포인트 거래(고유번호 출력)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "성공"),
            @ApiResponse(responseCode = "2004", description = "유효하지 않은 토큰입니다.", content = @Content),
            @ApiResponse(responseCode = "2012", description = "권한이 없는 유저의 접근입니다.", content = @Content),
            @ApiResponse(responseCode = "2103", description = "존재하지 않는 유저입니다.", content = @Content),
            @ApiResponse(responseCode = "4001", description = "서버 오류입니다.", content = @Content)
    })
    @PostMapping("/transaction")
    public ResponseEntity<BaseResponse<GetPointTransactionIdRes>> getPointTransactionId(@RequestBody GetPointTransactionIdReq getPointTransactionIdReq) {
        try {
            Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
            Long memberId = Long.parseLong(loggedInUser.getName());
            log.info("controller: {}",getPointTransactionIdReq.getPoint());
            GetPointTransactionIdRes getPointTransactionIdRes = pointMapper.toGetPointTransactionIdRes(pointService.getPointTransactionId(memberId, getPointTransactionIdReq.getPoint()));
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(getPointTransactionIdRes));

        } catch (BaseException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse<>(e.getStatus()));
        }
    }
    @Operation(summary = "포인트 거래(고유번호 입력)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "성공"),
            @ApiResponse(responseCode = "2004", description = "유효하지 않은 토큰입니다.", content = @Content),
            @ApiResponse(responseCode = "2012", description = "권한이 없는 유저의 접근입니다.", content = @Content),
            @ApiResponse(responseCode = "2103", description = "존재하지 않는 유저입니다.", content = @Content),
            @ApiResponse(responseCode = "4001", description = "서버 오류입니다.", content = @Content)
    })
    @PostMapping("/transaction/{TransactionId}")
    public ResponseEntity<BaseResponse<String>> TransactionPoint(@Parameter(name = "TransactionId", description = "거래 고유 번호", in = ParameterIn.PATH) @PathVariable String TransactionId) {
        try {
            Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
            Long memberId = Long.parseLong(loggedInUser.getName());
            pointService.TransactionPoint(memberId, TransactionId);
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(BaseResponseStatus.SUCCESS));
        } catch (BaseException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse<>(e.getStatus()));
        }
    }
}