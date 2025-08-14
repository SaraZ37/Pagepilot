package com.Pagepilot.Pagepilot.favorite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    // Get all favorite books for a user
    // URL: GET /api/favorites/user/1
    @GetMapping("/user/{userId}")
    public List<Favorite> getUserFavorites(@PathVariable Integer userId) {
        return favoriteService.getUserFavorites(userId);
    }

    // Add a book to favorites
    // URL: POST /api/favorites/user/1/book/1
    @PostMapping("/user/{userId}/book/{bookId}")
    public String addToFavorites(@PathVariable Integer userId, @PathVariable Integer bookId) {
        return favoriteService.addToFavorites(userId, bookId);
    }

    // Remove a book from favorites
    // URL: DELETE /api/favorites/user/1/book/1
    @DeleteMapping("/user/{userId}/book/{bookId}")
    public String removeFromFavorites(@PathVariable Integer userId, @PathVariable Integer bookId) {
        return favoriteService.removeFromFavorites(userId, bookId);
    }

    // Toggle favorite (add if not favorite, remove if favorite)
    // URL: PUT /api/favorites/user/1/book/1/toggle
    @PutMapping("/user/{userId}/book/{bookId}/toggle")
    public String toggleFavorite(@PathVariable Integer userId, @PathVariable Integer bookId) {
        return favoriteService.toggleFavorite(userId, bookId);
    }

    @GetMapping("/test")
    public String testFavorites() {
        return "Favorites API is working!";
    }
}
