package com.example.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckMailCommand {
    @NotNull
    String email;
    @NotNull
    String mailCode;
}
