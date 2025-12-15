package com.example.mhb.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "JWT token response")
public class TokenResponseDto {

    @Schema(description = "Access token (15 minutes)", example = "eyJhbGciOiJIUzI1NiJ9...")
    private String accessToken;

    @Schema(description = "Refresh token (long-lived)")
    private String refreshToken;

    @Schema(description = "Access token expiry time in seconds", example = "900")
    private long expiresIn;
}
