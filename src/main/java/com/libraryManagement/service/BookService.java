package com.libraryManagement.service;

import com.libraryManagement.dto.requestDto.BookRequestDto;
import com.libraryManagement.dto.responseDto.BookResponseDto;
import com.libraryManagement.entities.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    BookResponseDto addBook(BookRequestDto book);

    List<BookResponseDto> getAllBooks();

    Optional<BookResponseDto> getBookById(int id);

    BookResponseDto updateBook(int id, BookRequestDto updatedBook);

    void deleteBook(int id);

    List<BookResponseDto> searchByTitle(String title);

    List<BookResponseDto> searchByAuthor(String author);

    List<BookResponseDto> searchByGenre(String genre);
}