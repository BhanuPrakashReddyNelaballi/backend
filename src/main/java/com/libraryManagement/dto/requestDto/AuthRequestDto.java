package com.libraryManagement.dto.requestDto;

public class AuthRequestDto {
    private String email;
    private String password;

    // Default Constructor
    public AuthRequestDto() {}

    // Parameterized Constructor
    public AuthRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
