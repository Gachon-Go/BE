package com.example.infra.domain.member.adapter;

import com.example.domain.core.Profile;
import com.example.domain.port.output.repository.ProfileRepository;
import com.example.infra.domain.member.domain.ProfileEntity;
import com.example.infra.domain.member.mapper.ProfileDataAccessMapper;
import com.example.infra.domain.member.repository.ProfileJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
@AllArgsConstructor
public class ProfileRepositoryImp implements ProfileRepository {
    private final ProfileDataAccessMapper profileDataAccessMapper;
    private final ProfileJpaRepository profileJpaRepository;
    @Override
    public Optional<Profile> findProfileByEmail(String email) {
        Optional<ProfileEntity> profileEntity = profileJpaRepository.findByEmail(email);
        if(profileEntity.isEmpty())
            return Optional.empty();
        else {
            return Optional.ofNullable(profileDataAccessMapper.ProfileEntityToProfile(profileEntity.get()));
        }
    }
}
