package com.example.domain.core;

import com.example.core.model.Role;
import com.example.core.model.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;

@Builder
@Getter
@ToString
public class Member {
    private Long id;
    private String fcm_id;
    private Role role;
    private Status status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Profile profile;
}

