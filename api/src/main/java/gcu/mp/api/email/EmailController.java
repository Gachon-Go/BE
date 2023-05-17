package gcu.mp.api.email;

import gcu.mp.common.api.BaseResponse;
import gcu.mp.common.api.BaseResponseStatus;
import gcu.mp.mailclient.EmailService;
import gcu.mp.redis.Email;
import gcu.mp.service.member.MemberService;
import gcu.mp.util.Regex;
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
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
@Tag(name = "이메일")
public class EmailController {
    private final EmailService emailService;
    private final MemberService memberService;

    @Operation(summary = "이메일 인증번호 전송 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "성공", content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "2010", description = "유효한 이메일 정규식이 아닙니다.", content = @Content),
            @ApiResponse(responseCode = "2102", description = "사용중인 이메일입니다.", content = @Content),
            @ApiResponse(responseCode = "4001", description = "서버 오류입니다.", content = @Content)
    })
    @ResponseBody
    @PostMapping("")
    public ResponseEntity<BaseResponse<String>> sendMail(@RequestParam("address") String email) {
        try {
            if (!Regex.isRegexEmail(email))
                return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(BaseResponseStatus.REGEX_FAILED_EMAIL));
            if (memberService.existEmail(email)) {
                return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(BaseResponseStatus.EXISTS_EMAIL));
            }
            emailService.sendEmail(email);
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(BaseResponseStatus.SUCCESS));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse<>(BaseResponseStatus.SERVER_ERROR));
        }
    }

    @Operation(summary = "이메일 인증번호 인증 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "성공", content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "2011", description = "이메일 인증에 실패하였습니다.", content = @Content),
            @ApiResponse(responseCode = "4001", description = "서버 오류입니다.", content = @Content)
    })
    @PostMapping("/auth")
    @ResponseBody
    public ResponseEntity<BaseResponse<String>> checkMailCode(@Parameter(name = "mail", description = "이메일", in = ParameterIn.QUERY) @RequestParam("mail") String email,
                                                              @Parameter(name = "code", description = "이메일 인증 코드", in = ParameterIn.QUERY) @RequestParam("code") String emailCode) {
        try {
            boolean authCode = emailService.verifyEmailCode(new Email(email, emailCode));
            if (!authCode) {
                return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(BaseResponseStatus.MAIL_CERTIFICATION_FAIL));
            }
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(BaseResponseStatus.SUCCESS));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse<>(BaseResponseStatus.SERVER_ERROR));
        }
    }
}