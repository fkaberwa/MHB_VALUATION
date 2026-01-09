package com.example.mhb.dto.auth;

public class LoginResponseDto {

    private String accessToken;
    private String username;
    private String role;

    public LoginResponseDto(String accessToken, String username, String role) {
        this.accessToken = accessToken;
        this.username = username;
        this.role = role;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}
