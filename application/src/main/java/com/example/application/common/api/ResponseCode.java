package com.example.application.common.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    //TEST
    TEST(HttpStatus.OK, "TEST 입니다"),
    // member
    USER_SIGNUP(HttpStatus.CREATED,"회원 가입에 성공하였습니다."),
    USER_LOGIN(HttpStatus.OK,"로그인에 성공하였습니다.");
    // member
    private final HttpStatus status;
    private final String message;

}
