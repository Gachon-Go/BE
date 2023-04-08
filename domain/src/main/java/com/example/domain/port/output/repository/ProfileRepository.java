package com.example.domain.port.output.repository;

import com.example.domain.core.Profile;

import java.util.Optional;

public interface ProfileRepository {
    Optional<Profile> findProfileByEmail(String email);
}
