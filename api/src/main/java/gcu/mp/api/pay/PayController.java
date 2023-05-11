package gcu.mp.api.pay;

import gcu.mp.api.member.dto.request.ModifyNicknameRequest;
import gcu.mp.api.member.mapper.MemberMapper;
import gcu.mp.api.pay.dto.request.PayRequestReq;
import gcu.mp.api.pay.mapper.PayMapper;
import gcu.mp.common.api.BaseResponse;
import gcu.mp.common.api.BaseResponseStatus;
import gcu.mp.payclient.KakaoPayService;
import gcu.mp.payclient.dto.PayRequestResDto;
import gcu.mp.service.member.MemberService;
import gcu.mp.service.pay.PayService;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "결제")
@RequestMapping(value = "/pay")
public class PayController {
    private final KakaoPayService kakaoPayService;
    private final PayService payService;
    private final PayMapper payMapper;

    @Operation(summary = "결제요청")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "성공", content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "2103", description = "존재하지 않는 유저입니다.", content = @Content),
            @ApiResponse(responseCode = "4001", description = "서버 오류입니다.", content = @Content)
    })
    @PatchMapping("/request")
    public ResponseEntity<BaseResponse<PayRequestResDto>> payRequest(@RequestBody PayRequestReq payRequestReq) {
        try {
            Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
            Long memberId = Long.parseLong(loggedInUser.getName());
            PayRequestResDto payRequestResDto = kakaoPayService.payment(payMapper.toPayRequestDto(memberId, payRequestReq));
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(payRequestResDto));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse<>(BaseResponseStatus.SERVER_ERROR));
        }
    }

    @PatchMapping("/success")
    public ResponseEntity<BaseResponse<String>> paySuccess(@RequestBody PayRequestReq payRequestReq) {
        try {
            Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
            Long memberId = Long.parseLong(loggedInUser.getName());
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>("성공"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse<>(BaseResponseStatus.SERVER_ERROR));
        }
    }

    @PatchMapping("/fail")
    public ResponseEntity<BaseResponse<String>> payFail(@RequestBody PayRequestReq payRequestReq) {
        try {
            Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
            Long memberId = Long.parseLong(loggedInUser.getName());
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>("실패"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse<>(BaseResponseStatus.SERVER_ERROR));
        }
    }

    @PatchMapping("/cancel")
    public ResponseEntity<BaseResponse<String>> payCancel(@RequestBody PayRequestReq payRequestReq) {
        try {
            Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
            Long memberId = Long.parseLong(loggedInUser.getName());
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>("취소"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse<>(BaseResponseStatus.SERVER_ERROR));
        }
    }
}
