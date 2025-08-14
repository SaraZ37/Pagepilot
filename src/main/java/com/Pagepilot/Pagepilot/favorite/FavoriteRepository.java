package com.Pagepilot.Pagepilot.favorite;

import com.Pagepilot.Pagepilot.user.User;
import com.Pagepilot.Pagepilot.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {

    // Using a native SQL query to avoid field mapping issues
    @Query(value = "SELECT * FROM favorites WHERE user_id = ?1", nativeQuery = true)
    List<Favorite> findByUser(Integer userId);

    // Check if favorite exists
    @Query(value = "SELECT COUNT(*) FROM favorites WHERE user_id = ?1 AND book_id = ?2", nativeQuery = true)
    Integer countByUserAndBook(Integer userId, Integer bookId);

    // Delete favorite
    @Query(value = "DELETE FROM favorites WHERE user_id = ?1 AND book_id = ?2", nativeQuery = true)
    void deleteByUserAndBook(Integer userId, Integer bookId);
}

