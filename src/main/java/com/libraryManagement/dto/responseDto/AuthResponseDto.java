package com.libraryManagement.dto.responseDto;

public class AuthResponseDto {
    private String token;
    private String userRole;

    // Default Constructor
    public AuthResponseDto() {}

    // Parameterized Constructor
    public AuthResponseDto(String token, String userRole) {
        this.token = token;
        this.userRole = userRole;
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
