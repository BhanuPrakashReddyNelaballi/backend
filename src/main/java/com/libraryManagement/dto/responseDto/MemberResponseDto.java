package com.libraryManagement.dto.responseDto;

import com.libraryManagement.enums.MembershipStatus;
import com.libraryManagement.enums.UserRole;

public class MemberResponseDto {
    private Long memberId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private MembershipStatus membershipStatus;
    private UserRole userRole;


    public MemberResponseDto(Long memberId, String name, String email, String phone, String address, MembershipStatus membershipStatus, UserRole userRole) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.membershipStatus = membershipStatus;
        this.userRole = userRole;
    }



    public Long getMemberId() { return memberId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public MembershipStatus getMembershipStatus() { return membershipStatus; }
    public UserRole getUserRole() { return userRole; }
}
