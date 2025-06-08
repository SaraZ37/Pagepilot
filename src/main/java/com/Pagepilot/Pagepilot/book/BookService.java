package com.Pagepilot.Pagepilot.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> searchBooks(String keyword) {
        return bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(keyword, keyword);
    }

    public List<Book> filterBooks(String category, Integer year) {
        return bookRepository.findByCategorycodeAndPublicationYear(category, year);


    }
    public List<Book> getFavoriteBooks() {
        return bookRepository.findByIsFavoriteTrue();


    }public Book toggleFavorite(Integer bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        book.setIsFavorite(!Boolean.TRUE.equals(book.getIsFavorite())); //
        return bookRepository.save(book);
    }
}