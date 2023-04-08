package com.example.core.model;


import lombok.Getter;

public enum Role {

    USER(1),
    ADMIN(2);

    @Getter
    private final int statusCode;

    Role(int statusCode) {
        this.statusCode = statusCode;
    }
}