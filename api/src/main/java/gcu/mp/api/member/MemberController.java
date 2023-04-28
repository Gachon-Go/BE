package gcu.mp.api.member;

import gcu.mp.api.member.dto.request.ModifyNicknameRequest;
import gcu.mp.api.member.mapper.MemberMapper;
import gcu.mp.common.api.ApiResponse;
import gcu.mp.common.api.ResponseCode;
import gcu.mp.oauthclient.OAuthService;
import gcu.mp.security.SecurityConfig.jwt.JwtTokenProvider;
import gcu.mp.service.member.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "유저")
@RequestMapping(value = "/user")
public class MemberController {
    private final MemberMapper memberMapper;
    private final MemberService memberService;

    @Operation(summary = "닉네임 변경")
    @PatchMapping("/modify/nickname")
    public ResponseEntity<ApiResponse<String>> logIn(@RequestBody ModifyNicknameRequest modifyNicknameRequest) {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        Long memberId = Long.parseLong(loggedInUser.getName());
        if (memberService.existNickname(modifyNicknameRequest.getNickname())) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(ResponseCode.EXISTS_USER_NICKNAME));
        }
        memberService.modifyNickname(memberMapper.toModifyNicknameDto(modifyNicknameRequest, memberId));
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(ResponseCode.MODIFY_NICKNAME));
    }
    @Operation(summary = "회원탈퇴")
    @DeleteMapping("/resign")
    public ResponseEntity<ApiResponse<String>> resign(){
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        Long memberId = Long.parseLong(loggedInUser.getName());
        memberService.resignMember(memberId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(ResponseCode.RESIGN_MEMBER));
    }
}
