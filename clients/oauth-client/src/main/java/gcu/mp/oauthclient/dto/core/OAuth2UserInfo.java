package gcu.mp.oauthclient.dto.core;

import java.util.Map;

public interface OAuth2UserInfo {
    Map<String, Object> getAttributes();

    String getProviderId();

    String getProfileImage();

    OAuthType getProvider();
}