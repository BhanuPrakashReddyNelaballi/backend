package com.libraryManagement.dto.requestDto;

import com.libraryManagement.enums.MembershipStatus;
import com.libraryManagement.enums.UserRole;

public class MemberRequestDto {
    private String name;
    private String email;
    private String phone;
    private String address;
    private String password;
    private UserRole userRole;
    private MembershipStatus membershipStatus;

    public MemberRequestDto() {}

    public MemberRequestDto(String name, String email, String phone, String address, String password, UserRole userRole, MembershipStatus membershipStatus) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.password = password;
        this.userRole = userRole;
        this.membershipStatus = membershipStatus;
    }

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

    public UserRole getUserRole() { return userRole; }
    public void setUserRole(UserRole userRole) { this.userRole = userRole; }

    public MembershipStatus isMembershipStatus() { return membershipStatus; }
    public void setMembershipStatus(MembershipStatus membershipStatus) { this.membershipStatus = membershipStatus; }
}
