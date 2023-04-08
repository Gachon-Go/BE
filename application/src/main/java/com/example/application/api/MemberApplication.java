package com.example.application.api;

import com.example.core.dto.CreateMemberCommand;
import com.example.core.dto.CreateMemberResponse;
import com.example.domain.port.input.MemberApplicationService;
import com.example.core.common.api.ApiResponse;
import com.example.core.common.api.ResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class MemberApplication {
    private final MemberApplicationService memberApplicationService;

    @PostMapping(value = "/signup")
    public ResponseEntity<ApiResponse<CreateMemberResponse>> SignUp(@RequestBody CreateMemberCommand createMemberCommand)  {
        CreateMemberResponse createMemberResponse = memberApplicationService.createMember(createMemberCommand);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(ResponseCode.USER_SIGNUP,createMemberResponse));
    }
}