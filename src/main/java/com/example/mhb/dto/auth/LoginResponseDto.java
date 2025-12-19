package com.example.mhb.dto.auth;

public class LoginResponseDto {

    private String accessToken;
    private UserDetailsDto userDetails;

    public LoginResponseDto(String accessToken, String username, String role) {
        this.accessToken = accessToken;
        this.userDetails = new UserDetailsDto(username, role);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public UserDetailsDto getUserDetails() {
        return userDetails;
    }

    public static class UserDetailsDto {
        private String username;
        private String role;

        public UserDetailsDto(String username, String role) {
            this.username = username;
            this.role = role;
        }

        public String getUsername() {
            return username;
        }

        public String getRole() {
            return role;
        }
    }
}
