package com.libraryManagement.dto.responseDto;

public class DashboardStatsDto {

    private long totalBooks;
    private long totalMembers;

    public long getTotalBooks() {
        return totalBooks;
    }

    public void setTotalBooks(long totalBooks) {
        this.totalBooks = totalBooks;
    }

    public long getTotalMembers() {
        return totalMembers;
    }

    public void setTotalMembers(long totalMembers) {
        this.totalMembers = totalMembers;
    }

    public DashboardStatsDto(long totalBooks, long totalMembers) {
        this.totalBooks = totalBooks;
        this.totalMembers = totalMembers;
    }

    public DashboardStatsDto() {
    }
}
