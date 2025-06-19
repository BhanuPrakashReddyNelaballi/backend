package com.libraryManagement.dto.requestDto;

public class MemberUpdateDto {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String name;
    private String Phone;
    private String address;

    public MemberUpdateDto(String name, String phone, String address) {
        this.name = name;
        this.Phone = phone;
        this.address = address;
    }
}
