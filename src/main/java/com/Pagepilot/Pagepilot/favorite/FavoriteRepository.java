package com.Pagepilot.Pagepilot.favorite;

import com.Pagepilot.Pagepilot.user.User;
import com.Pagepilot.Pagepilot.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {

    List<Favorite> findByUser(User user);

    // Check if favorite exists
    boolean existsByUserAndBook(User user, Book book);

    // Find specific favorite
    Optional<Favorite> findByUserAndBook(User user, Book book);

   // Delete favorite
    void deleteByUserAndBook(User user, Book book);
}

