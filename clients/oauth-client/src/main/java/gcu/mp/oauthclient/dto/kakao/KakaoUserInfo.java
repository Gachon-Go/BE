package gcu.mp.oauthclient.dto.kakao;


import gcu.mp.oauthclient.dto.core.OAuth2UserInfo;
import gcu.mp.oauthclient.dto.core.OAuthType;

import java.util.Map;


public class KakaoUserInfo implements OAuth2UserInfo {
    private final Map<String, Object> attributes;
    private final Map<String, Object> attributesAccount;
    private final Map<String, Object> attributesProfile;

    @SuppressWarnings("unchecked")
    public KakaoUserInfo(Map<String, Object> attributes) {

        this.attributes = attributes;
        this.attributesAccount = (Map<String, Object>) attributes.get("kakao_account");
        this.attributesProfile = (Map<String, Object>) attributesAccount.get("profile");
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
    public String getProfileImage() {
        return attributesProfile.get("profile_image_url").toString();
    }

    @Override
    public OAuthType getProvider() {
        return OAuthType.Kakao;
    }

    public Map<String, Object> getAttributesAccount() {
        return attributesProfile;
    }

    public Map<String, Object> getAttributesProfile() {
        return attributesProfile;
    }
}
