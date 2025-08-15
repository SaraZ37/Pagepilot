package com.Pagepilot.Pagepilot.favorite;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@Tag(name = "Favorites", description = "US-04: Manage favorite books")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    // Get all favorite books for a user
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get user's favorites", description = "Get all favorite books for a user")
    public List<Favorite> getUserFavorites
    (@Parameter(description = "User ID") @PathVariable Integer userId) {
        return favoriteService.getUserFavorites(userId);
    }

    // Add a book to favorites
    @PostMapping("/user/{userId}/book/{bookId}")
    @Operation(summary = "Add to favorites", description = "Add a book to user's favorites")
    public String addToFavorites(
    @Parameter(description = "User ID") @PathVariable Integer userId,
    @Parameter(description = "Book ID") @PathVariable Integer bookId) {
        return favoriteService.addToFavorites(userId, bookId);
    }

    // Remove a book from favorites
    // URL: DELETE /api/favorites/user/1/book/1
    @DeleteMapping("/user/{userId}/book/{bookId}")
    @Operation(summary = "Remove from favorites", description = "Remove a book from user's favorites")
    public String removeFromFavorites(
            @Parameter(description = "User ID") @PathVariable Integer userId,
            @Parameter(description = "Book ID") @PathVariable Integer bookId) {
        return favoriteService.removeFromFavorites(userId, bookId);
    }

    // Toggle favorite (add if not favorite, remove if favorite)
    // URL: PUT /api/favorites/user/1/book/1/toggle
    @PutMapping("/user/{userId}/book/{bookId}/toggle")
    @Operation(summary = "Toggle favorite", description = "Add to favorites if not favorite, remove if already favorite")
    public String toggleFavorite(
            @Parameter(description = "User ID") @PathVariable Integer userId,
            @Parameter(description = "Book ID") @PathVariable Integer bookId) {
        return favoriteService.toggleFavorite(userId, bookId);
    }

    @GetMapping("/test")
    public String testFavorites() {
        return "Favorites API is working!";
    }
}
