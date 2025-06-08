package com.Pagepilot.Pagepilot.book;

import com.Pagepilot.Pagepilot.author.Author;
import com.Pagepilot.Pagepilot.loan.Loan;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jdk.jfr.Description;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;


@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer bookId;

    @Column(name = "title")
    private String title;

    @Column(name = "year")
    private Integer publicationYear;

    @Column(name = "category")
    private String category;

    @Column(name = "is_available")
    private Integer isAvailable;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    /*@JsonManagedReference */
    @JsonIgnore
    @Description("@JsonIgnore do not let me see the author when I open /api/books, but I want to see it")
    private Author author;

    @OneToMany(mappedBy = "book")
    @JsonIgnore
    private Set<Loan> loans;

    public Book(Integer bookId, String title, Integer publicationYear, String category, Integer isAvailable, Author author, Set<Loan> loans) {
        this.bookId = bookId;
        this.title = title;
        this.publicationYear = publicationYear;
        this.category = category;
        this.isAvailable = isAvailable;
        this.author = author;
        this.loans = loans;
    }

    public Book() {

    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Integer isAvailable) {
        this.isAvailable = isAvailable;
    }

    @JsonProperty("authorName")
    public String getAuthorName() {
        return author != null ? author.getName() : null;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Set<Loan> getLoans() {
        return loans;
    }
    @JsonProperty("loanID")
    public List<Integer> getLoanID() {
        return loans != null ? loans.stream()
                .map(Loan::getLoanId)
                .toList() : null;
    }

    public void setLoans(Set<Loan> loans) {
        this.loans = loans;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", publicationYear=" + publicationYear +
                ", category='" + category + '\'' +
                ", isAvailable=" + isAvailable +
                ", author=" + (author != null ? author.getName() : "null") +
                ", loanIDs=" + getLoanID() +
                '}';
    }
}
