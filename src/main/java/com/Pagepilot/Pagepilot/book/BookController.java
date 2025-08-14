package com.Pagepilot.Pagepilot.book;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@Tag(name = "Book Management", description = "APIs for managing books in the library")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Get all books
    @GetMapping
    @Operation(summary = "Get all books", description = "Shows the list of all books")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    // Search by title or author (US02)
    @GetMapping("/search")//http://localhost:8080/books/search?q=sali
    @Operation(summary = "Search books", description = "Search books by title or author name")
    public ResponseEntity<?> searchBooks(
            @Parameter(description = "Search keyword for title or author")
            @RequestParam("q") String keyword) {
        List<Book> results = bookService.searchBooks(keyword);
        if (results.isEmpty()) {
            return ResponseEntity.ok("No books found");
        }
        return ResponseEntity.ok(results);
    }

    // Filter by category and year (US03)
    @GetMapping("/filter")//need to refine it because now need to use both param http://localhost:8080/books/filter?category=Fiction&year=1960
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