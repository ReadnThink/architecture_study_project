package com.example.sns_project.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
public record SignUp(
        @NotBlank
        @Email
        String email,

        @Size(min = 8)
        String password,

        @NotBlank
        String name
) {
}
