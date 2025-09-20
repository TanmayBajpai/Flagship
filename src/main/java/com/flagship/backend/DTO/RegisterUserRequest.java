package com.flagship.backend.DTO;

import jakarta.validation.constraints.NotBlank;

public record RegisterUserRequest(
        @NotBlank String username,
        @NotBlank String password
) {
}
