package com.Pagepilot.Pagepilot.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    /*List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String title, String author);*/
    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(b.author.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String title, String authorName);
    List<Book> findByCategoryAndPublicationYear(String category, Integer publicationYear);
List<Book> findByIsFavoriteTrue();
}