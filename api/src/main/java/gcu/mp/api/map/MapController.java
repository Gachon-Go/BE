package gcu.mp.api.map;

import gcu.mp.api.map.dto.request.PostMapPointReq;
import gcu.mp.api.map.dto.response.GetMapInformationRes;
import gcu.mp.api.map.mapper.MapMapper;
import gcu.mp.common.api.BaseResponse;
import gcu.mp.common.api.BaseResponseStatus;
import gcu.mp.common.exception.BaseException;
import gcu.mp.service.deliveryPost.DeliveryPostService;
import gcu.mp.service.map.MapService;
import gcu.mp.service.orderPost.OrderPostService;
import io.swagger.v3.oas.annotations.Operation;
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


@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "맵")
@RequestMapping(value = "/map")
public class MapController {
    private final MapService mapService;
    private final MapMapper mapMapper;
    private final OrderPostService orderPostService;
    private final DeliveryPostService deliveryPostService;

    @Operation(summary = "위치 전송")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "성공", content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "2004", description = "유효하지 않은 토큰입니다.", content = @Content),
            @ApiResponse(responseCode = "2012", description = "권한이 없는 유저의 접근입니다.", content = @Content),
            @ApiResponse(responseCode = "2103", description = "존재하지 않는 유저입니다.", content = @Content),
            @ApiResponse(responseCode = "4001", description = "서버 오류입니다.", content = @Content)
    })
    @PostMapping("")
    public ResponseEntity<BaseResponse<String>> postMapPoint(@RequestBody PostMapPointReq postMapPointReq) {
        try {
            Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
            long memberId = Long.parseLong(loggedInUser.getName());
            mapService.postMapPoint(mapMapper.toPostMapPointDto(memberId, postMapPointReq));
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(BaseResponseStatus.SUCCESS));
        } catch (BaseException e) {
            return ResponseEntity.status(e.getStatus().getHttpCode()).body(new BaseResponse<>(e.getStatus()));
        }
    }

    @Operation(summary = "위치 받기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "성공"),
            @ApiResponse(responseCode = "2004", description = "유효하지 않은 토큰입니다.", content = @Content),
            @ApiResponse(responseCode = "2012", description = "권한이 없는 유저의 접근입니다.", content = @Content),
            @ApiResponse(responseCode = "2103", description = "존재하지 않는 유저입니다.", content = @Content),
            @ApiResponse(responseCode = "4001", description = "서버 오류입니다.", content = @Content)
    })
    @GetMapping("")
    public ResponseEntity<BaseResponse<GetMapInformationRes>> getMapPoints() {
        try {
            Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
            long memberId = Long.parseLong(loggedInUser.getName());
            GetMapInformationRes getMapInformationRes = mapMapper.toGetMapPointsResList(mapService.getMapInformation(memberId));
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(getMapInformationRes));
        } catch (BaseException e) {
            return ResponseEntity.status(e.getStatus().getHttpCode()).body(new BaseResponse<>(e.getStatus()));
        }
    }
}