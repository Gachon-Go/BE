package gcu.mp.api.email;

import gcu.mp.common.api.ApiResponse;
import gcu.mp.common.api.ErrorCode;
import gcu.mp.common.api.ResponseCode;
import gcu.mp.common.exception.CustomException;
import gcu.mp.mailclient.EmailService;
import gcu.mp.redis.Email;
import gcu.mp.util.Regex;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
public class EmailController {
    private final EmailService emailService;

    @Operation(summary = "이메일 인증번호 전송 API")
    @ResponseBody
    @PostMapping("")
    public ResponseEntity<ApiResponse<String>> sendMail(@RequestParam("address") String email) {
        if(!Regex.isRegexEmail(email))
            throw new CustomException(ErrorCode.REGEX_FAILED_EMAIL);
        emailService.sendEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(ResponseCode.MAIL_CERTIFICATION));
    }

    @Operation(summary = "이메일 인증번호 인증 API")
    @PostMapping("/auth")
    @ResponseBody
    public ResponseEntity<ApiResponse<String>> checkMailCode(@RequestParam("mail") String email, @RequestParam("code") String emailCode) {
        boolean authCode = emailService.verifyEmailCode(new Email(email, emailCode));
        if (!authCode) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(ResponseCode.MAIL_CERTIFICATION_FAIL));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(ResponseCode.MAIL_CERTIFICATION_AUTH));
    }
}