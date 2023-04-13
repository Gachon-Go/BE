package gcu.project.gachongo.infra.ouath.core;

import java.util.Map;

public interface OAuth2UserInfo {
    Map<String, Object> getAttributes();
    String getProviderId();
    OAuthType getProvider();
}