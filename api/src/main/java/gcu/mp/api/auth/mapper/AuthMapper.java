package gcu.mp.api.auth.mapper;

import gcu.mp.api.auth.dto.request.CreateMemberReq;
import gcu.mp.oauthclient.dto.core.OAuth2UserInfo;
import gcu.mp.service.member.dto.CreateMemberDto;
import gcu.mp.service.member.dto.OauthMemberDto;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {
    public OauthMemberDto toOauthMemberDto(OAuth2UserInfo oAuth2UserInfo){
        return OauthMemberDto.builder()
                .oauthType(oAuth2UserInfo.getProvider().toString())
                .Token(oAuth2UserInfo.getProviderId()).build();
    }
    public CreateMemberDto toCreateMemberDto(CreateMemberReq createMemberReq, OAuth2UserInfo oAuth2UserInfo){
        return CreateMemberDto.builder()
                .providerId(oAuth2UserInfo.getProviderId())
                .provider(oAuth2UserInfo.getProvider().toString())
                .email(createMemberReq.getEmail())
                .nickname(createMemberReq.getNickname())
                .imageUrl(oAuth2UserInfo.getProfileImage()).build();
    }
}
