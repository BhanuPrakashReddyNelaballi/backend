package com.libraryManagement.dto.requestDto;

public class NotificationRequestDto {
    private Long memberId;
    private String message;

    public NotificationRequestDto() {}

    public NotificationRequestDto(Long memberId, String message) {
        this.memberId = memberId;
        this.message = message;
    }

    public Long getMemberId() { return memberId; }
    public void setMemberId(Long memberId) { this.memberId = memberId; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}