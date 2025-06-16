package com.libraryManagement.dto.requestDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Incoming payload for /api/auth/register.
 * ‑‑ NO role or membership fields here ‑‑
 */
public class RegisterRequestDto {

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Email must be valid")
    @NotBlank(message = "Email is required")
    private String email;

    private String phone;
    private String address;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    public RegisterRequestDto() {}

    public RegisterRequestDto(String name, String email,
                              String phone, String address,
                              String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.password = password;
    }

    /* getters & setters */
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
