package com.Pagepilot.Pagepilot.loan;

import com.Pagepilot.Pagepilot.book.Book;
import com.Pagepilot.Pagepilot.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "loans")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer loanId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    @Column(name = "borrow_date")
    private LocalDate borrowedDate;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    public Loan(Integer loanId, User user, Book book, LocalDate borrowedDate, LocalDate dueDate, LocalDate returnDate) {
        this.loanId = loanId;
        this.user = user;
        this.book = book;
        this.borrowedDate = borrowedDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
    }

    public Loan() {

    }

    public Loan(LoanRequestDto loanRequestDto) {
    }

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    @JsonProperty("userID")
    public Integer getUserID() {

        return user != null ? user.getUserId() : null;
    }
    @JsonProperty("userName")
    public String getUserName() {

        return user != null ? user.getUsername() : null;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @JsonProperty("bookID")
    public Integer getBookID() {

        return book != null ? book.getBookId() : null;
    }
    @JsonProperty("bookTitle")
    public String getBookTitle() {

        return book != null ? book.getTitle() : null;
    }
    @JsonProperty("bookAuthor")
    public String getBookAuthor() {

        return book != null ? book.getAuthorName() : null;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getBorrowedDate() {
        return borrowedDate;
    }

    public void setBorrowedDate(LocalDate borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "loanId=" + loanId +
                ", userID=" + user.getUserId() +
                ", userName=" + user.getUsername() +
                ", bookTitle=" + book.getTitle() +
                ", bookAuthor=" + book.getAuthorName() +
                ", borrowedDate=" + borrowedDate +
                ", dueDate=" + dueDate +
                ", returnDate=" + returnDate +
                '}';
    }
}
