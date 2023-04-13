package gcu.project.gachongo.infra.ouath.core;

import gcu.project.gachongo.global.common.api.ErrorCode;
import gcu.project.gachongo.global.common.exception.CustomException;
import gcu.project.gachongo.infra.ouath.kakao.KakaoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OAuthService {
    private final KakaoService kakaoService;

    public OAuth2UserInfo identificationProvider(OAuthType provider, String token) {
        OAuth2UserInfo oAuth2UserInfo;
        if (provider.equals(OAuthType.Kakao)) {
            oAuth2UserInfo = kakaoService.getUserInfoByKakaoToken(token);
        } else {
            throw new CustomException(ErrorCode.INVALID_PROVIDER);
        }
        return oAuth2UserInfo;
    }
}