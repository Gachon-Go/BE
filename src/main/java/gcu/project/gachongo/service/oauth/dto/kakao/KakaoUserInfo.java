package gcu.project.gachongo.service.oauth.dto.kakao;

import gcu.project.gachongo.service.oauth.dto.core.OAuth2UserInfo;
import gcu.project.gachongo.service.oauth.dto.core.OAuthType;

import java.util.Map;


public class KakaoUserInfo implements OAuth2UserInfo {
    private final Map<String, Object> attributes;
    public KakaoUserInfo(Map<String, Object> attributes) {

        this.attributes = attributes;

    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }

    @Override
    public OAuthType getProvider() {
        return OAuthType.Kakao;
    }
}
