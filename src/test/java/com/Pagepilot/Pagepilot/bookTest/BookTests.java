package com.Pagepilot.Pagepilot.bookTest;

import com.Pagepilot.Pagepilot.book.Book;
import com.Pagepilot.Pagepilot.book.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class BookTests {

    private final BookService bookService;

    public BookTests(BookService bookService) {
        this.bookService = bookService;
    }

    @Test
    public void testGetAllBooks() {
        List<Book> books = bookService.getAllBooks();
        assertNotNull(books, "List of books should not be null");
        assertFalse(books.isEmpty(), "List of books should not be empty");
        books.forEach(book -> System.out.println(book));
    }
}