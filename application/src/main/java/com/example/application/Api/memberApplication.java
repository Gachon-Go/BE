package com.example.application.Api;

import com.example.application.common.api.ApiResponse;
import com.example.application.common.api.ResponseCode;
import com.example.domain.domain.member.domain.Member;
import com.example.domain.domain.member.repository.MemberRepository;
import com.example.domain.domain.member.service.MemberService;
import com.example.domain.domain.model.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class memberApplication {
    private final MemberService memberService;
    @PostMapping(value = "/signup")
    public ResponseEntity<ApiResponse<String>> SignUp()  {

        memberService.createUser();
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(ResponseCode.USER_SIGNUP));
    }
    @GetMapping(value = "/find")
    public ResponseEntity<ApiResponse<List<Member>>> findUser()  {

        List<Member> memberList = memberService.findUser();
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(ResponseCode.USER_SIGNUP,memberList));
    }
}
