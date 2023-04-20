package gcu.project.gachongo.api.auth;

import gcu.project.gachongo.api.auth.dto.request.CreateMemberRequest;
import gcu.project.gachongo.global.common.api.ApiResponse;
import gcu.project.gachongo.global.common.api.ErrorCode;
import gcu.project.gachongo.global.common.api.ResponseCode;
import gcu.project.gachongo.global.common.exception.CustomException;
import gcu.project.gachongo.service.oauth.dto.core.OAuth2UserInfo;
import gcu.project.gachongo.service.member.MemberService;
import gcu.project.gachongo.service.oauth.OAuthService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "유저")
@RequestMapping(value = "/user")
public class AuthController {
    private final OAuthService oAuthService;
    private final MemberService memberService;

    @PostMapping(value = "/signup")
    public ResponseEntity<ApiResponse<String>> SignUp(@RequestBody CreateMemberRequest createMemberRequest) {
        OAuth2UserInfo oAuth2UserInfo = oAuthService.identificationProvider(createMemberRequest.getProvider(), createMemberRequest.getToken());
        if (memberService.existMember(oAuth2UserInfo.getProvider(), oAuth2UserInfo.getProviderId())) {
            throw new CustomException(ErrorCode.EXISTS_MEMBER);
        }
        if (memberService.existNickname(createMemberRequest.getNickname())) {
            throw new CustomException(ErrorCode.EXISTS_USER_NICKNAME);
        }
        if (memberService.existEmail(createMemberRequest.getEmail())) {
            throw new CustomException(ErrorCode.EXISTS_EMAIL);
        }
        memberService.createMember(createMemberRequest, oAuth2UserInfo);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(ResponseCode.USER_SIGNUP));
    }
}
