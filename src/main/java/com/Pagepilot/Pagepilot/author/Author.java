package com.Pagepilot.Pagepilot.author;

import com.Pagepilot.Pagepilot.book.Book;
import com.Pagepilot.Pagepilot.loan.Loan;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jdk.jfr.Description;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer authorId;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "author")
    private Set<Book> books;

    public Author(Integer authorId, String name, Set<Book> books) {
        this.authorId = authorId;
        this.name = name;
        this.books = books;
    }

    public Author() {

    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("bookTitles")
    public List<String> getBookTitles() {
        return books != null ? books.stream()
                .map(Book::getTitle)
                .toList() : null;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Author{" +
                "authorId=" + authorId +
                ", name='" + name + '\'' +
                /*", books=" + books +*/
                '}';
    }
}
