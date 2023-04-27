package gcu.mp.oauthclient;

import gcu.mp.oauthclient.dto.core.OAuth2UserInfo;
import gcu.mp.oauthclient.dto.core.OAuthType;
import gcu.mp.oauthclient.provider.KakaoService;
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
            oAuth2UserInfo = null;
        }
        return oAuth2UserInfo;
    }
}