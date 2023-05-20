package gcu.mp.api.map;

import gcu.mp.api.map.dto.request.PostMapPointReq;
import gcu.mp.api.map.mapper.MapMapper;
import gcu.mp.common.api.BaseResponse;
import gcu.mp.common.api.BaseResponseStatus;
import gcu.mp.service.deliveryPost.DeliveryPostService;
import gcu.mp.service.map.MapService;
import gcu.mp.service.map.dto.GetMapPointDto;
import gcu.mp.service.orderPost.OrderPostService;
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

import static gcu.mp.common.api.BaseResponseStatus.INVALID_PURPOSE_VALUE;

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
            if (postMapPointReq.getPurpose().equals("order")) {
                if (!orderPostService.existOrderPost(postMapPointReq.getPostId())) {
                    return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(BaseResponseStatus.NOT_EXIST_POST));
                }
            }
            //todo delivery post 개발시 추가
            else if(postMapPointReq.getPurpose().equals("delivery")){
                if(!deliveryPostService.existDeliveryPost(postMapPointReq.getPostId())){
                    return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(BaseResponseStatus.NOT_EXIST_POST));
                }
            }
            else
                return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(INVALID_PURPOSE_VALUE));
            mapService.postMapPoint(mapMapper.toPostMapPointDto(memberId, postMapPointReq));
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(BaseResponseStatus.SUCCESS));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse<>(BaseResponseStatus.SERVER_ERROR));
        }
    }

    @Operation(summary = "위치 받기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "성공", content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "2004", description = "유효하지 않은 토큰입니다.", content = @Content),
            @ApiResponse(responseCode = "2012", description = "권한이 없는 유저의 접근입니다.", content = @Content),
            @ApiResponse(responseCode = "2103", description = "존재하지 않는 유저입니다.", content = @Content),
            @ApiResponse(responseCode = "4001", description = "서버 오류입니다.", content = @Content)
    })
    @GetMapping("")
    public ResponseEntity<BaseResponse<List<GetMapPointDto>>> getMapPoints(@Parameter(name = "purpose", description = "order 또는 delivery", in = ParameterIn.QUERY) @RequestParam String purpose,
                                                                           @Parameter(name = "postId", description = "포스트 id", in = ParameterIn.QUERY) @RequestParam Long postId) {
        try {
            Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
            long memberId = Long.parseLong(loggedInUser.getName());
            if (purpose.equals("order")) {
                if (!orderPostService.existOrderPost(postId)) {
                    return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(BaseResponseStatus.NOT_EXIST_POST));
                }
            }
            //todo delivery post 개발시 추가
            else if(purpose.equals("delivery")){
                if(!deliveryPostService.existDeliveryPost(postId)){
                    return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(BaseResponseStatus.NOT_EXIST_POST));
                }
            }
            else
                return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(INVALID_PURPOSE_VALUE));
            List<GetMapPointDto> getMapPointDtoList = mapService.getMapPointList(purpose, postId);
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(getMapPointDtoList));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse<>(BaseResponseStatus.SERVER_ERROR));
        }
    }
}
