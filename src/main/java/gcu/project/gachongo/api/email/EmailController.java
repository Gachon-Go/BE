package gcu.project.gachongo.api.email;

import gcu.project.gachongo.domain.member.vo.Email;
import gcu.project.gachongo.global.common.api.ApiResponse;
import gcu.project.gachongo.global.common.api.ResponseCode;
import gcu.project.gachongo.global.util.Regex;
import gcu.project.gachongo.service.email.EmailService;
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

    @ResponseBody
    @PostMapping("")
    public ResponseEntity<ApiResponse<String>> sendMail(@RequestParam("address") String email) {
        Regex.isRegexEmail(email);
        emailService.sendEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(ResponseCode.MAIL_CERTIFICATION));
    }

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