package com.Pagepilot.Pagepilot.book;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String title, String author);
    List<Book> findByCategorycodeAndPublicationYear(String categorycode, Integer publicationYear);
List<Book> findByIsFavoriteTrue();
}