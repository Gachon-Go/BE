package gcu.mp.api.auth;

import gcu.mp.api.auth.dto.request.CreateMemberRequest;
import gcu.mp.api.auth.mapper.AuthMapper;
import gcu.mp.common.api.ApiResponse;
import gcu.mp.common.api.ErrorCode;
import gcu.mp.common.api.ResponseCode;
import gcu.mp.common.exception.CustomException;
import gcu.mp.oauthclient.OAuthService;
import gcu.mp.oauthclient.dto.core.OAuth2UserInfo;
import gcu.mp.service.member.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "유저")
@RequestMapping(value = "/users")
public class AuthController {
    private final AuthMapper authMapper;
    private final OAuthService oAuthService;
    private final MemberService memberService;

    @PostMapping(value = "/signup")
    @Operation(summary ="회원가입 API")
    public ResponseEntity<ApiResponse<String>> SignUp(@RequestBody CreateMemberRequest createMemberRequest) {
        OAuth2UserInfo oAuth2UserInfo = oAuthService.identificationProvider(createMemberRequest.getProvider(), createMemberRequest.getToken());
        if (memberService.existMember(authMapper.toExistMemberDto(oAuth2UserInfo))) {
            throw new CustomException(ErrorCode.EXISTS_MEMBER);
        }
        if (memberService.existNickname(createMemberRequest.getNickname())) {
            throw new CustomException(ErrorCode.EXISTS_USER_NICKNAME);
        }
        if (memberService.existEmail(createMemberRequest.getEmail())) {
            throw new CustomException(ErrorCode.EXISTS_EMAIL);
        }
        memberService.createMember(authMapper.toCreateMemberDto(createMemberRequest, oAuth2UserInfo));

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(ResponseCode.USER_SIGNUP));
    }

}
