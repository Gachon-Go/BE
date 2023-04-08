package com.example.domain.core;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;

@Builder
@Getter
@ToString
public class Profile {
    private Long id;
    private String nickname;
    private String email;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
