package com.Pagepilot.Pagepilot.favorite;

import com.Pagepilot.Pagepilot.user.User;
import com.Pagepilot.Pagepilot.user.UserRepository;
import com.Pagepilot.Pagepilot.book.Book;
import com.Pagepilot.Pagepilot.book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    // Get all favorite books for a user
    public List<Favorite> getUserFavorites(Integer userId) {
        return favoriteRepository.findByUser(userId);
    }

    // Add a book to favorites
    public String addToFavorites(Integer userId, Integer bookId) {
        // Check if already in favorites
        Integer count = favoriteRepository.countByUserAndBook(userId, bookId);
        if (count > 0) {
            return "Book is already in favorites";
        }

        // Find user and book
        User user = userRepository.findById(userId).orElse(null);
        Book book = bookRepository.findById(bookId).orElse(null);

        if (user == null) {
            return "User not found";
        }
        if (book == null) {
            return "Book not found";
        }

        // Add to favorites
        Favorite favorite = new Favorite(user, book);
        favoriteRepository.save(favorite);
        return "Book added to favorites successfully";
    }

    // Remove books from favorites
    public String removeFromFavorites(Integer userId, Integer bookId) {
        // Check if a book is in favorites
        Integer count = favoriteRepository.countByUserAndBook(userId, bookId);
        if (count == 0) {
            return "Book is not in favorites";
        }

        // Remove from favorites
        favoriteRepository.deleteByUserAndBook(userId, bookId);
        return "Book removed from favorites successfully";
    }

    // Toggle favorite
    public String toggleFavorite(Integer userId, Integer bookId) {
        Integer count = favoriteRepository.countByUserAndBook(userId, bookId);
        if (count > 0) {
            favoriteRepository.deleteByUserAndBook(userId, bookId);
            return "Book removed from favorites";
        } else {
            return addToFavorites(userId, bookId);
        }
    }
}
