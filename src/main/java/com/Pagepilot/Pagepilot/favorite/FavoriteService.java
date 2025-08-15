package com.Pagepilot.Pagepilot.favorite;

import com.Pagepilot.Pagepilot.user.User;
import com.Pagepilot.Pagepilot.user.UserRepository;
import com.Pagepilot.Pagepilot.book.Book;
import com.Pagepilot.Pagepilot.book.BookRepository;
import jakarta.transaction.Transactional;
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
        User user = userRepository.findByUserId(userId).orElse(null);
        if (user == null) {
            return List.of();
        }
        return favoriteRepository.findByUser(user);
    }

    // Add a book to favorites
    public String addToFavorites(Integer userId, Integer bookId) {
        // Find user and book
        User user = userRepository.findByUserId(userId).orElse(null);
        Book book = bookRepository.findByBookId(bookId).orElse(null);

        if (user == null) {
            return "User not found";
        }
        if (book == null) {
            return "Book not found";
        }

        // Check if already in favorites
        if (favoriteRepository.existsByUserAndBook(user, book)) {
            return "Book is already in favorites";
        }

        // Add to favorites
        Favorite favorite = new Favorite(user, book);
        favoriteRepository.save(favorite);
        return "Book added to favorites successfully";
    }

    // Remove books from favorites
    @Transactional
    public String removeFromFavorites(Integer userId, Integer bookId) {
        // Find user and book
        User user = userRepository.findByUserId(userId).orElse(null);
        Book book = bookRepository.findByBookId(bookId).orElse(null);


        if (user == null) {
            return "User not found";
        }
        if (book == null) {
            return "Book not found";
        }

        // Check if the book is in favorites
        if (!favoriteRepository.existsByUserAndBook(user, book)) {
            return "Book is not in favorites";
        }

        // Remove from favorites
        favoriteRepository.deleteByUserAndBook(user,book);
        return "Book removed from favorites successfully";
    }

    // Toggle favorite
    @Transactional
    public String toggleFavorite(Integer userId, Integer bookId) {
        User user = userRepository.findByUserId(userId).orElse(null);
        Book book = bookRepository.findByBookId(bookId).orElse(null);
        if (user == null) {
            return "User not found";
        }
        if (book == null) {
            return "Book not found";
        }

        if (favoriteRepository.existsByUserAndBook(user, book)) {
            favoriteRepository.deleteByUserAndBook(user, book);
            return "Book removed from favorites";
        } else {
            Favorite favorite = new Favorite(user, book);
            favoriteRepository.save(favorite);
            return "Book added to favorites";
        }
    }
}
