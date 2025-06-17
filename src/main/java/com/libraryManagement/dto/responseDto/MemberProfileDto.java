package com.libraryManagement.dto.responseDto;

import com.libraryManagement.enums.MembershipStatus;

public class MemberProfileDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private MembershipStatus membershipStatus;
    // getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public MembershipStatus getMembershipStatus() {
        return membershipStatus;
    }

    public void setMembershipStatus(MembershipStatus membershipStatus) {
        this.membershipStatus = membershipStatus;
    }

    public MemberProfileDto() {
    }

    public MemberProfileDto(Long id, String name, String email, String phone, String address, MembershipStatus membershipStatus) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.membershipStatus = membershipStatus;
    }
}