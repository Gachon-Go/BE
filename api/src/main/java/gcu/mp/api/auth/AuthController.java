package gcu.mp.api.auth;

import gcu.mp.api.auth.dto.request.CreateMemberRequest;
import gcu.mp.api.auth.dto.request.LoginMemberRequest;
import gcu.mp.api.auth.dto.response.LogInMemberResponse;
import gcu.mp.api.auth.mapper.AuthMapper;
import gcu.mp.common.api.BaseResponse;
import gcu.mp.common.api.BaseResponseStatus;
import gcu.mp.oauthclient.OAuthService;
import gcu.mp.oauthclient.dto.core.OAuth2UserInfo;
import gcu.mp.security.SecurityConfig.jwt.JwtTokenProvider;
import gcu.mp.service.member.MemberService;
import gcu.mp.service.member.dto.LoginMemberDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "인증")
@RequestMapping(value = "/auth")
public class AuthController {
    private final AuthMapper authMapper;
    private final OAuthService oAuthService;
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping(value = "/signup")
    @Operation(summary = "회원가입 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "성공", content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "2100", description = "이미 존재하는 유저입니다.", content = @Content),
            @ApiResponse(responseCode = "2101", description = "사용중인 유저 닉네임 입니다.", content = @Content),
            @ApiResponse(responseCode = "2102", description = "사용중인 이메일입니다.", content = @Content),
            @ApiResponse(responseCode = "4001", description = "서버 오류입니다.", content = @Content)
    })
    public ResponseEntity<BaseResponse<String>> signUp(@RequestBody CreateMemberRequest createMemberRequest) {
        try {
            OAuth2UserInfo oAuth2UserInfo = oAuthService.identificationProvider(createMemberRequest.getProvider(), createMemberRequest.getToken());
            if (memberService.existMember(authMapper.toOauthMemberDto(oAuth2UserInfo))) {
                return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(BaseResponseStatus.EXISTS_MEMBER));
            }
            if (memberService.existNickname(createMemberRequest.getNickname())) {
                return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(BaseResponseStatus.EXISTS_MEMBER_NICKNAME));
            }
            if (memberService.existEmail(createMemberRequest.getEmail())) {
                return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(BaseResponseStatus.EXISTS_EMAIL));
            }
            memberService.createMember(authMapper.toCreateMemberDto(createMemberRequest, oAuth2UserInfo));

            return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(BaseResponseStatus.SUCCESS));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse<>(BaseResponseStatus.SERVER_ERROR));
        }
    }

    @PostMapping(value = "/login")
    @Operation(summary = "로그인 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "성공", content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "2004", description = "유효하지 않은 토큰입니다.", content = @Content),
            @ApiResponse(responseCode = "2103", description = "존재하지 않는 유저입니다.", content = @Content),
            @ApiResponse(responseCode = "4001", description = "서버 오류입니다.", content = @Content)
    })
    public ResponseEntity<BaseResponse<LogInMemberResponse>> logIn(@RequestBody LoginMemberRequest loginMemberRequest) {
        try {
            OAuth2UserInfo oAuth2UserInfo = oAuthService.identificationProvider(loginMemberRequest.getProvider(), loginMemberRequest.getToken());
            Long memberId = memberService.getMemberId(authMapper.toOauthMemberDto(oAuth2UserInfo));
            if (memberId == null)
                return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(BaseResponseStatus.NOT_EXIST_MEMBER));
            LoginMemberDto loginMemberDto = memberService.getLonginMember(memberId);
            LogInMemberResponse logInMemberResponse = new LogInMemberResponse(memberId, jwtTokenProvider.createAccessToken(Long.toString(memberId)), loginMemberDto);
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(logInMemberResponse));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse<>(BaseResponseStatus.SERVER_ERROR));
        }
    }

    @GetMapping(value = "/nickname/{nickname}/validation")
    @Operation(summary = "닉네임 유효성 체크 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "성공", content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "2101", description = "사용중인 유저 닉네임 입니다.", content = @Content),
            @ApiResponse(responseCode = "4001", description = "서버 오류입니다.", content = @Content)
    })
    public ResponseEntity<BaseResponse<String>> ValidationUserNickName(@Parameter(name = "nickname", description = "유효성 체크할 닉네임", in = ParameterIn.PATH) @PathVariable String nickname) {
        try {
            if (memberService.existNickname(nickname)) {
                return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(BaseResponseStatus.EXISTS_MEMBER_NICKNAME));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(BaseResponseStatus.SUCCESS));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse<>(BaseResponseStatus.SERVER_ERROR));
        }
    }
}
