package gcu.mp.api.auth;

import gcu.mp.api.auth.dto.request.CreateMemberRequest;
import gcu.mp.api.auth.dto.request.LoginMemberRequest;
import gcu.mp.api.auth.dto.response.LogInMemberResponse;
import gcu.mp.api.auth.mapper.AuthMapper;
import gcu.mp.common.api.ApiResponse;
import gcu.mp.common.api.ErrorCode;
import gcu.mp.common.api.ResponseCode;
import gcu.mp.common.exception.CustomException;
import gcu.mp.oauthclient.OAuthService;
import gcu.mp.oauthclient.dto.core.OAuth2UserInfo;
import gcu.mp.security.SecurityConfig.jwt.JwtTokenProvider;
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
@RequestMapping(value = "/auth")
public class AuthController {
    private final AuthMapper authMapper;
    private final OAuthService oAuthService;
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping(value = "/signup")
    @Operation(summary = "회원가입 API")
    public ResponseEntity<ApiResponse<String>> signUp(@RequestBody CreateMemberRequest createMemberRequest) {
        OAuth2UserInfo oAuth2UserInfo = oAuthService.identificationProvider(createMemberRequest.getProvider(), createMemberRequest.getToken());
        if (memberService.existMember(authMapper.toOauthMemberDto(oAuth2UserInfo))) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(ResponseCode.EXISTS_MEMBER));
        }
        if (memberService.existNickname(createMemberRequest.getNickname())) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(ResponseCode.EXISTS_USER_NICKNAME));
        }
        if (memberService.existEmail(createMemberRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(ResponseCode.EXISTS_EMAIL));
        }
        memberService.createMember(authMapper.toCreateMemberDto(createMemberRequest, oAuth2UserInfo));

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(ResponseCode.USER_SIGNUP));
    }

    @PostMapping(value = "/login")
    @Operation(summary = "로그인 API")
    public ResponseEntity<ApiResponse<LogInMemberResponse>> logIn(@RequestBody LoginMemberRequest loginMemberRequest) {
        OAuth2UserInfo oAuth2UserInfo = oAuthService.identificationProvider(loginMemberRequest.getProvider(), loginMemberRequest.getToken());
        Long memberId = memberService.getMemberId(authMapper.toOauthMemberDto(oAuth2UserInfo));
        if (memberId == null)
            throw new CustomException(ErrorCode.NO_EXISTS_USER);
        LogInMemberResponse logInMemberResponse = new LogInMemberResponse(jwtTokenProvider.createAccessToken(Long.toString(memberId)));
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(ResponseCode.USER_LOGIN, logInMemberResponse));
    }

    @GetMapping(value = "/nickname/{nickname}/validation")
    @Operation(summary = "닉네임 유효성 체크 API")
    public ResponseEntity<ApiResponse<String>> ValidationUserNickName(@PathVariable String nickname) {
        if (memberService.existNickname(nickname)) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(ResponseCode.EXISTS_USER_NICKNAME));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(ResponseCode.USER_NICKNAME));
    }
}
