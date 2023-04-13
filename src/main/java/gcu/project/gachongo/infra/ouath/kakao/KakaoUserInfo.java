package gcu.project.gachongo.infra.ouath.kakao;

import gcu.project.gachongo.infra.ouath.core.OAuth2UserInfo;
import gcu.project.gachongo.infra.ouath.core.OAuthType;
import lombok.Getter;

import java.util.Map;

@Getter
public class KakaoUserInfo implements OAuth2UserInfo {
    private final Map<String, Object> attributes;
//    private final Map<String, Object> attributesAccount;
//    private final Map<String, Object> attributesProfile;
    @SuppressWarnings("unchecked")
    public KakaoUserInfo(Map<String, Object> attributes) {
        /*
        System.out.println(attributes);
            {id=아이디값,
            connected_at=2022-02-22T15:50:21Z,
            properties={nickname=이름},
            kakao_account={
                profile_nickname_needs_agreement=false,
                profile={nickname=이름},
                has_email=true,
                email_needs_agreement=false,
                is_email_valid=true,
                is_email_verified=true,
                email=이메일}
            }
        */
        this.attributes = attributes;
//        this.attributesAccount = (Map<String, Object>) attributes.get("kakao_account");
//        this.attributesProfile = (Map<String, Object>) attributesAccount.get("profile");
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
