package com.Pagepilot.Pagepilot.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Get all books
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    // Search by title or author (US02)
    @GetMapping("/search")
    public ResponseEntity<?> searchBooks(@RequestParam("q") String keyword) {
        List<Book> results = bookService.searchBooks(keyword);
        if (results.isEmpty()) {
            return ResponseEntity.ok("No books found");
        }
        return ResponseEntity.ok(results);
    }

    // Filter by category and year (US03)
    @GetMapping("/filter")
    public ResponseEntity<?> filterBooks(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer year) {

        // Clear filter: return all books if no parameters sent
        if ((category == null || category.isEmpty()) && year == null) {
            return ResponseEntity.ok(bookService.getAllBooks());
        }

        List<Book> results = bookService.filterBooks(category, year);
        if (results.isEmpty()) {
            return ResponseEntity.ok("No books found for this filter");
        }
        return ResponseEntity.ok(results);
    }
    @GetMapping("/favorites")
    public ResponseEntity<?> getFavoriteBooks() {
        List<Book> favorites = bookService.getFavoriteBooks();
        if (favorites.isEmpty()) {
            return ResponseEntity.ok("No favorite books found.");
        } else {
            return ResponseEntity.ok(favorites);
        }

    }@PutMapping("/favorites/{id}")
    public ResponseEntity<Book> toggleFavorite(@PathVariable Integer id) {
        Book updatedBook = bookService.toggleFavorite(id);
        return ResponseEntity.ok(updatedBook);
    }
}