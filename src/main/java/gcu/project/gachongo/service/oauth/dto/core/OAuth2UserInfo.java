package gcu.project.gachongo.service.oauth.dto.core;

import java.util.Map;

public interface OAuth2UserInfo {
    Map<String, Object> getAttributes();
    String getProviderId();
    OAuthType getProvider();
}