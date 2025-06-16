package com.libraryManagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.libraryManagement.dto.requestDto.BookRequestDto;
import com.libraryManagement.dto.responseDto.BookResponseDto;
import com.libraryManagement.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
//@WebMvcTest(BookController.class)
@SpringBootTest
public class BookControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    void setUp() {
        Mockito.reset(bookService);
    }

    @Test
    void testAddBook_ReturnsBookResponseDto() throws Exception {
        mockMvc= MockMvcBuilders.standaloneSetup(bookController).build();
        BookRequestDto bookRequestDto = new BookRequestDto("Effective Java", "Joshua Bloch", "Programming", "123456789", 2001, 5);
        BookResponseDto bookResponseDto = new BookResponseDto(1, "Effective Java", "Joshua Bloch", "Programming", "123456789", 2001, 5);

        Mockito.when(bookService.addBook(Mockito.any(BookRequestDto.class))).thenReturn(bookResponseDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/books/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Effective Java"))
                .andExpect(jsonPath("$.author").value("Joshua Bloch"));
    }

    @Test
    void testGetBooks_ReturnsBookList() throws Exception {
        mockMvc= MockMvcBuilders.standaloneSetup(bookController).build();
        BookResponseDto bookResponseDto = new BookResponseDto(1, "Effective Java", "Joshua Bloch", "Programming", "123456789", 2001, 5);

        Mockito.when(bookService.getAllBooks()).thenReturn(Collections.singletonList(bookResponseDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/getbooks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].title").value("Effective Java"));
    }

    @Test
    void testGetBookById_ReturnsBookResponseDto() throws Exception {
        mockMvc= MockMvcBuilders.standaloneSetup(bookController).build();
        int bookId = 1;
        BookResponseDto bookResponseDto = new BookResponseDto(bookId, "Effective Java", "Joshua Bloch", "Programming", "123456789", 2001, 5);

        Mockito.when(bookService.getBookById(bookId)).thenReturn(Optional.of(bookResponseDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/{id}", bookId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Effective Java"));
    }

    @Test
    void testUpdateBook_ReturnsUpdatedBookResponseDto() throws Exception {
        mockMvc= MockMvcBuilders.standaloneSetup(bookController).build();
        int bookId = 1;
        BookRequestDto updatedBookRequestDto = new BookRequestDto("Effective Java", "Joshua Bloch", "Programming", "123456789", 2001, 10);
        BookResponseDto updatedBookResponseDto = new BookResponseDto(bookId, "Effective Java", "Joshua Bloch", "Programming", "123456789", 2001, 10);

        Mockito.when(bookService.updateBook(Mockito.eq(bookId), Mockito.any(BookRequestDto.class))).thenReturn(updatedBookResponseDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/books/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBookRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.availableCopies").value(10));
    }

    @Test
    void testDeleteBook_ReturnsNoContent() throws Exception {
        mockMvc= MockMvcBuilders.standaloneSetup(bookController).build();
        int bookId = 1;

        Mockito.doNothing().when(bookService).deleteBook(bookId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/{id}", bookId))
                .andExpect(status().isOk());
    }

    @Test
    void testSearchByTitle_ReturnsMatchingBooks() throws Exception {
        mockMvc= MockMvcBuilders.standaloneSetup(bookController).build();
        String title = "Effective Java";
        BookResponseDto bookResponseDto = new BookResponseDto(1, title, "Joshua Bloch", "Programming", "123456789", 2001, 5);

        Mockito.when(bookService.searchByTitle(title)).thenReturn(Collections.singletonList(bookResponseDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/search/title")
                        .param("title", title))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].title").value(title));
    }

    @Test
    void testSearchByAuthor_ReturnsMatchingBooks() throws Exception {
        mockMvc= MockMvcBuilders.standaloneSetup(bookController).build();
        String author = "Joshua Bloch";
        BookResponseDto bookResponseDto = new BookResponseDto(1, "Effective Java", author, "Programming", "123456789", 2001, 5);

        Mockito.when(bookService.searchByAuthor(author)).thenReturn(Collections.singletonList(bookResponseDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/search/author")
                        .param("author", author))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].author").value(author));
    }

    @Test
    void testSearchByGenre_ReturnsMatchingBooks() throws Exception {
        mockMvc= MockMvcBuilders.standaloneSetup(bookController).build();
        String genre = "Programming";
        BookResponseDto bookResponseDto = new BookResponseDto(1, "Effective Java", "Joshua Bloch", genre, "123456789", 2001, 5);

        Mockito.when(bookService.searchByGenre(genre)).thenReturn(Collections.singletonList(bookResponseDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/search/genre")
                        .param("genre", genre))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].genre").value(genre));
    }
}




















//package com.libraryManagement.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.libraryManagement.dto.requestDto.BookRequestDto;
//import com.libraryManagement.entities.Book;
//import com.libraryManagement.service.BookService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.Collections;
//import java.util.Optional;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(SpringExtension.class)
////@WebMvcTest(BookController.class)
//@SpringBootTest
//public class BookControllerTest {
//
//    private MockMvc mockMvc;
//
//    @Mock
//    private BookService bookService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @InjectMocks
//    private BookController bookController;
//
//    @BeforeEach
//    void setUp() {
//        Mockito.reset(bookService);
//    }
//
//    @Test
//    void testAddBook_ReturnsBook() throws Exception {
//        mockMvc= MockMvcBuilders.standaloneSetup(bookController).build();
//        BookRequestDto book = new BookRequestDto("Effective Java", "Joshua Bloch", "Programming", "123456789", 2001, 5);
//
//        Mockito.when(bookService.addBook(Mockito.any(Book.class))).thenReturn(book);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/books/add")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(book)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.title").value("Effective Java"))
//                .andExpect(jsonPath("$.author").value("Joshua Bloch"));
//    }
//
//    @Test
//    void testGetBooks_ReturnsBookList() throws Exception {
//        mockMvc= MockMvcBuilders.standaloneSetup(bookController).build();
//        Book book = new Book(1, "Effective Java", "Joshua Bloch", "Programming", "123456789", 2001, 5);
//
//        Mockito.when(bookService.getAllBooks()).thenReturn(Collections.singletonList(book));
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/getbooks"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.size()").value(1))
//                .andExpect(jsonPath("$[0].title").value("Effective Java"));
//    }
//
//    @Test
//    void testGetBookById_ReturnsBook() throws Exception {
//        mockMvc= MockMvcBuilders.standaloneSetup(bookController).build();
//        int bookId = 1;
//        Book book = new Book(bookId, "Effective Java", "Joshua Bloch", "Programming", "123456789", 2001, 5);
//
//        Mockito.when(bookService.getBookById(bookId)).thenReturn(Optional.of(book));
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/{id}", bookId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.title").value("Effective Java"));
//    }
//
//    @Test
//    void testUpdateBook_ReturnsUpdatedBook() throws Exception {
//        mockMvc= MockMvcBuilders.standaloneSetup(bookController).build();
//        int bookId = 1;
//        Book updatedBook = new Book(bookId, "Effective Java", "Joshua Bloch", "Programming", "123456789", 2001, 10);
//
//        Mockito.when(bookService.updateBook(Mockito.eq(bookId), Mockito.any(Book.class))).thenReturn(updatedBook);
//
//        mockMvc.perform(MockMvcRequestBuilders.put("/api/books/{id}", bookId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(updatedBook)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.availableCopies").value(10));
//    }
//
//    @Test
//    void testDeleteBook_ReturnsNoContent() throws Exception {
//        mockMvc= MockMvcBuilders.standaloneSetup(bookController).build();
//        int bookId = 1;
//
//        Mockito.doNothing().when(bookService).deleteBook(bookId);
//
//        mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/{id}", bookId))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void testSearchByTitle_ReturnsMatchingBooks() throws Exception {
//        mockMvc= MockMvcBuilders.standaloneSetup(bookController).build();
//        String title = "Effective Java";
//        Book book = new Book(1, title, "Joshua Bloch", "Programming", "123456789", 2001, 5);
//
//        Mockito.when(bookService.searchByTitle(title)).thenReturn(Collections.singletonList(book));
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/search/title")
//                        .param("title", title))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.size()").value(1))
//                .andExpect(jsonPath("$[0].title").value(title));
//    }
//
//    @Test
//    void testSearchByAuthor_ReturnsMatchingBooks() throws Exception {
//        mockMvc= MockMvcBuilders.standaloneSetup(bookController).build();
//        String author = "Joshua Bloch";
//        Book book = new Book(1, "Effective Java", author, "Programming", "123456789", 2001, 5);
//
//        Mockito.when(bookService.searchByAuthor(author)).thenReturn(Collections.singletonList(book));
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/search/author")
//                        .param("author", author))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.size()").value(1))
//                .andExpect(jsonPath("$[0].author").value(author));
//    }
//
//    @Test
//    void testSearchByGenre_ReturnsMatchingBooks() throws Exception {
//        mockMvc= MockMvcBuilders.standaloneSetup(bookController).build();
//        String genre = "Programming";
//        Book book = new Book(1, "Effective Java", "Joshua Bloch", genre, "123456789", 2001, 5);
//
//        Mockito.when(bookService.searchByGenre(genre)).thenReturn(Collections.singletonList(book));
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/search/genre")
//                        .param("genre", genre))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.size()").value(1))
//                .andExpect(jsonPath("$[0].genre").value(genre));
//    }
//}