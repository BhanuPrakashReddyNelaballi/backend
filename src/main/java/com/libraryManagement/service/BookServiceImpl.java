package com.libraryManagement.service;

import com.libraryManagement.dto.requestDto.BookRequestDto;
import com.libraryManagement.dto.responseDto.BookResponseDto;
import com.libraryManagement.repository.BookRepository;
import com.libraryManagement.entities.Book;
import com.libraryManagement.exceptions.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookResponseDto addBook(BookRequestDto bookDto) {
        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setGenre(bookDto.getGenre());
        book.setIsbn(bookDto.getIsbn());
        book.setYearPublished(bookDto.getYearPublished());
        book.setAvailableCopies(bookDto.getAvailableCopies());
        Book savedBook = bookRepository.save(book);
        return new BookResponseDto(savedBook.getBookId(), savedBook.getTitle(), savedBook.getAuthor(), savedBook.getGenre(), savedBook.getIsbn(), savedBook.getYearPublished(), savedBook.getAvailableCopies());
    }

    @Override
    public List<BookResponseDto> getAllBooks() {
        return bookRepository.findAll().stream().map(book -> new BookResponseDto(book.getBookId(), book.getTitle(), book.getAuthor(), book.getGenre(), book.getIsbn(), book.getYearPublished(), book.getAvailableCopies())).collect(Collectors.toList());
    }

    @Override
    public Optional<BookResponseDto> getBookById(int id) {
        return bookRepository.findById(id).map(book -> new BookResponseDto(book.getBookId(), book.getTitle(), book.getAuthor(), book.getGenre(), book.getIsbn(), book.getYearPublished(), book.getAvailableCopies()));
    }

    @Override
    public BookResponseDto updateBook(int id, BookRequestDto updatedBookDto) {
        return bookRepository.findById(id).map(book -> {
            book.setTitle(updatedBookDto.getTitle());
            book.setAuthor(updatedBookDto.getAuthor());
            book.setGenre(updatedBookDto.getGenre());
            book.setIsbn(updatedBookDto.getIsbn());
            book.setYearPublished(updatedBookDto.getYearPublished());
            book.setAvailableCopies(updatedBookDto.getAvailableCopies());
            Book updatedBook = bookRepository.save(book);
            return new BookResponseDto(updatedBook.getBookId(), updatedBook.getTitle(), updatedBook.getAuthor(), updatedBook.getGenre(), updatedBook.getIsbn(), updatedBook.getYearPublished(), updatedBook.getAvailableCopies());
        }).orElseThrow(() -> new BookNotFoundException("Book not found with ID: " + id));
    }

    @Override
    public void deleteBook(int id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found with ID: " + id));
        bookRepository.deleteById(book.getBookId());
    }

    @Override
    public List<BookResponseDto> searchByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title).stream().map(book -> new BookResponseDto(book.getBookId(), book.getTitle(), book.getAuthor(), book.getGenre(), book.getIsbn(), book.getYearPublished(), book.getAvailableCopies())).collect(Collectors.toList());
    }

    @Override
    public List<BookResponseDto> searchByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author).stream().map(book -> new BookResponseDto(book.getBookId(), book.getTitle(), book.getAuthor(), book.getGenre(), book.getIsbn(), book.getYearPublished(), book.getAvailableCopies())).collect(Collectors.toList());
    }

    @Override
    public List<BookResponseDto> searchByGenre(String genre) {
        return bookRepository.findByGenreContainingIgnoreCase(genre).stream().map(book -> new BookResponseDto(book.getBookId(), book.getTitle(), book.getAuthor(), book.getGenre(), book.getIsbn(), book.getYearPublished(), book.getAvailableCopies())).collect(Collectors.toList());
    }
}