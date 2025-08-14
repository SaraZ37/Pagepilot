package com.Pagepilot.Pagepilot.favorite;

import com.Pagepilot.Pagepilot.user.User;
import com.Pagepilot.Pagepilot.book.Book;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "favorites")
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    public Favorite() {}

    // Constructor with user and book
    public Favorite(User user, Book book) {
        this.user = user;
        this.book = book;
    }

    // getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    // Helper methods to get book info
    public String getBookTitle() {
        return book != null ? book.getTitle() : null;
    }

    public Integer getBookId() {
        return book != null ? book.getBookId() : null;
    }

}
