package com.example.core.common.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    //TEST
    TEST(HttpStatus.OK, "TEST 입니다"),
    // member
    USER_SIGNUP(HttpStatus.CREATED, "회원 가입에 성공하였습니다."),
    USER_LOGIN(HttpStatus.OK, "로그인에 성공하였습니다."),
    // member
    MAIL_CERTIFICATION(HttpStatus.OK, "이메일 인증에 성공적으로 전송하였습니다."),
    MAIL_CERTIFICATION_AUTH(HttpStatus.OK, "이메일 인증에 성공하였습니다."),
    MAIL_CERTIFICATION_FAIL(HttpStatus.OK, "이메일 인증에 실패하였습니다.");
    private final HttpStatus status;
    private final String message;

}
