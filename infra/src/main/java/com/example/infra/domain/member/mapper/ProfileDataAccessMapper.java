package com.example.infra.domain.member.mapper;

import com.example.domain.core.Profile;
import com.example.infra.domain.member.domain.ProfileEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class ProfileDataAccessMapper {
    public Profile ProfileEntityToProfile(ProfileEntity profileEntity) {
        Profile profile = Profile.builder()
                .id(profileEntity.getId())
                .nickname(profileEntity.getNickname())
                .email(profileEntity.getEmail())
                .createdAt(profileEntity.getCreatedAt())
                .updatedAt(profileEntity.getUpdatedAt())
                .build();
        return profile;
    }
}
