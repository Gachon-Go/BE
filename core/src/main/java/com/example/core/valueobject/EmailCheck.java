package com.example.core.valueobject;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmailCheck {
    String email;
    String code;
}
