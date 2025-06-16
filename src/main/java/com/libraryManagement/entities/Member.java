package com.libraryManagement.entities;

import com.libraryManagement.enums.MembershipStatus;
import com.libraryManagement.enums.UserRole;
import jakarta.persistence.*;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String name;


    @Column(unique = true, nullable = false, updatable = false)
    private String email;

    private String phone;
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MembershipStatus membershipStatus = MembershipStatus.ACTIVE;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole userRole = UserRole.MEMBER;   // ← default member role

    public Member() {
    }

    public Member(String name, String email,
                  String phone, String address,
                  String password, UserRole userRole) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.password = password;
        this.userRole = userRole;
    }

    /* getters & setters */
    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
