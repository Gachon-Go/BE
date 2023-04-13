package gcu.project.gachongo.domain.member.application;

import gcu.project.gachongo.domain.member.dto.request.CreateMemberDto;
import gcu.project.gachongo.domain.member.service.MemberService;
import gcu.project.gachongo.global.common.api.ApiResponse;
import gcu.project.gachongo.global.common.api.ErrorCode;
import gcu.project.gachongo.global.common.api.ResponseCode;
import gcu.project.gachongo.global.common.exception.CustomException;
import gcu.project.gachongo.infra.ouath.core.OAuth2UserInfo;
import gcu.project.gachongo.infra.ouath.core.OAuthService;
import gcu.project.gachongo.infra.ouath.kakao.KakaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "유저")
@RequestMapping(value = "/users")
public class MemberApplication {
    private final OAuthService oAuthService;
    private final MemberService memberService;


    @PostMapping(value = "/signup")
    public ResponseEntity<ApiResponse<String>> SignUp(@RequestBody CreateMemberDto createMemberDto) {
        OAuth2UserInfo oAuth2UserInfo = oAuthService.identificationProvider(createMemberDto.getProvider(), createMemberDto.getToken());
        if (memberService.existMember(oAuth2UserInfo.getProvider(), oAuth2UserInfo.getProviderId())) {
            throw new CustomException(ErrorCode.EXISTS_MEMBER);
        }
        if (memberService.existNickname(createMemberDto.getNickname())) {
            throw new CustomException(ErrorCode.EXISTS_USER_NICKNAME);
        }
        if (memberService.existEmail(createMemberDto.getEmail())) {
            throw new CustomException(ErrorCode.EXISTS_EMAIL);
        }
        memberService.createMember(createMemberDto, oAuth2UserInfo);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(ResponseCode.USER_SIGNUP));
    }

}