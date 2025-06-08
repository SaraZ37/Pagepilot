package com.Pagepilot.Pagepilot.book;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private static final Logger log = LogManager.getLogger(BookController.class);
    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getAllBooks(){
        List<Book> books = bookService.getAllBooks();
        log.info("Fetching all books: " + books);
        System.out.println("Fetching all books: " + books);
        return books;
    }

}
