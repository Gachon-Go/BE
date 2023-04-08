package com.example.application.api;

import com.example.core.dto.CheckMailCommand;
import com.example.core.dto.EmailCommand;
import com.example.domain.port.input.EmailApplicationService;
import com.example.core.common.api.ApiResponse;
import com.example.core.common.api.ResponseCode;
import com.example.infra.system.util.Regex;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
public class EmailApplication {
    private final EmailApplicationService emailApplicationService;

    @PostMapping("")
    @ResponseBody
    public ResponseEntity<ApiResponse<String>> sendMail(@RequestParam(value = "mail") EmailCommand emailCommand) {
        Regex.isRegexEmail(emailCommand.getEmail());
        emailApplicationService.sendEmail(emailCommand);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(ResponseCode.MAIL_CERTIFICATION));
    }

    @PostMapping("/auth")
    @ResponseBody
    public ResponseEntity<ApiResponse<String>> checkMailCode(@RequestParam("mail") String email, @RequestParam("code") String emailCode) {
        boolean authCode = emailApplicationService.verifyEmailCode(new CheckMailCommand(email, emailCode));
        if (!authCode) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(ResponseCode.MAIL_CERTIFICATION_FAIL));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(ResponseCode.MAIL_CERTIFICATION_AUTH));
    }
}
