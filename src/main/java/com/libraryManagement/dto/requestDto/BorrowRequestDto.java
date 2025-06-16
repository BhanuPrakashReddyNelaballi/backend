package com.libraryManagement.dto.requestDto;

public class BorrowRequestDto {
    private Long memberId;
    private Integer bookId;

    public BorrowRequestDto() {}

    public BorrowRequestDto(Long memberId, Integer bookId) {
        this.memberId = memberId;
        this.bookId = bookId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

}