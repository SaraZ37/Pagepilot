package com.Pagepilot.Pagepilot.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {
    /*List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String title, String author);*/
    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(b.author.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Book> searchBooks(@Param("keyword") String keyword);
    List<Book> findByCategoryAndYear(String category, Integer year);
    List<Book> findByCategoryOrYear(String category, Integer year);
List<Book> findByIsFavoriteTrue();

    Optional <Book> findByBookId(Integer bookId);
}