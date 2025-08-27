package com.Pagepilot.Pagepilot.loan;

import com.Pagepilot.Pagepilot.book.Book;
import com.Pagepilot.Pagepilot.book.BookRepository;
import com.Pagepilot.Pagepilot.user.User;
import com.Pagepilot.Pagepilot.user.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.ArrayList;


@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public LoanService(LoanRepository loanRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public List<Loan> getAllLoans(){
        return loanRepository.findAll();
    }
    public List<Loan> getAllUserLoans(User user) {
        return loanRepository.findByUser(user);
    }
    public List<Loan> getCurrentUserLoans(User user) {
        List<Loan> loans = loanRepository.findByUser(user);
        return loans.stream()
                .filter(loan -> loan.getReturnDate() == null)
                .toList();
    }
    public List<Loan> getPastUserLoans(User user) {
        List<Loan> loans = loanRepository.findByUser(user);
        return loans.stream()
                .filter(loan -> loan.getReturnDate() != null)
                .toList();
    }

    public Loan createNewLoan(LoanRequestDto loanRequestDto) {

        User user = userRepository.findByUserId(loanRequestDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + loanRequestDto.getUserId()));
        Book book = bookRepository.findByBookId(loanRequestDto.getBookId()).orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + loanRequestDto.getBookId()));
        Integer isAvailable = book.getIsAvailable();
        if (isAvailable == 1) {
            book.setIsAvailable(0);
            bookRepository.save(book);

            Loan loan = new Loan();
            loan.setUser(user);
            loan.setBook(book);
            loan.setBorrowedDate(LocalDateTime.now());
            loan.setDueDate(LocalDateTime.now().plusWeeks(2));
            loan.setReturnDate(null);

            return loanRepository.save(loan);
        } else {
            throw new IllegalStateException("Book is not available for loan.");
        }
    }

    // US-05: Check if user has overdue books
    public boolean userHasOverdueBooks(Integer userId) {
        try {
            // Get all loans for this user
            List<Loan> allLoans = loanRepository.findAll();
            LocalDateTime now = LocalDateTime.now();

            for (Loan loan : allLoans) {
                // Check if this loan belongs to the user and is overdue
                if (loan.getUserID() != null &&
                        loan.getUserID().equals(userId) &&
                        loan.getReturnDate() == null &&
                        loan.getDueDate() != null &&
                        loan.getDueDate().isBefore(now)) {
                    return true; // Found an overdue book
                }
            }
            return false; // No overdue books found
        } catch (Exception e) {
            return false; // If error, assume no overdue books
        }
    }

    // US-05: Check if book is currently borrowed
    public boolean isBookCurrentlyBorrowed(Integer bookId) {
        try {
            // Get all loans
            List<Loan> allLoans = loanRepository.findAll();

            for (Loan loan : allLoans) {
                // Check if this loan is for this book and not returned
                if (loan.getBookID() != null &&
                        loan.getBookID().equals(bookId) &&
                        loan.getReturnDate() == null) {
                    return true; // Book is currently borrowed
                }
            }
            return false; // Book is not borrowed
        } catch (Exception e) {
            return false; // If error, assume book is available
        }
    }

    // US-05: Get when book will be available
    public String getBookAvailabilityDate(Integer bookId) {
        try {
            List<Loan> allLoans = loanRepository.findAll();

            for (Loan loan : allLoans) {
                if (loan.getBookID() != null &&
                        loan.getBookID().equals(bookId) &&
                        loan.getReturnDate() == null) {
                    return "Book will be available after " + loan.getDueDate().toLocalDate();
                }
            }
            return "Available now";
        } catch (Exception e) {
            return "Availability unknown";
        }
    }

    // US-05:  borrow book with all validations
    public String borrowBook(Integer userId, Integer bookId) {
        try {
            // Step 1: Check if user has overdue books
            if (userHasOverdueBooks(userId)) {
                return "Error: You have overdue books. Please return them before borrowing new books.";
            }

            // Step 2: Check if book is available
            if (isBookCurrentlyBorrowed(bookId)) {
                String availabilityInfo = getBookAvailabilityDate(bookId);
                return "Error: This book is currently borrowed. " + availabilityInfo;
            }

            // Step 3: Check if user and book exist
            User user = userRepository.findByUserId(userId).orElse(null);
            Book book = bookRepository.findByBookId(bookId).orElse(null);

            if (user == null) {
                return "Error: User not found";
            }
            if (book == null) {
                return "Error: Book not found";
            }

            // Step 4: Check if book is marked as available in database
            if (book.getIsAvailable() != 1) {
                return "Error: Book is not available for borrowing";
            }

            // Step 5: Borrow the book
            // Mark book as borrowed
            book.setIsAvailable(0);
            bookRepository.save(book);

            // Create loan record
            Loan loan = new Loan();
            loan.setUser(user);
            loan.setBook(book);
            loan.setBorrowedDate(LocalDateTime.now());
            loan.setDueDate(LocalDateTime.now().plusDays(14)); // 14 days loan period
            loan.setReturnDate(null); // Not returned yet

            loanRepository.save(loan);

            // Step 6: Return success message
            return String.format("Success! You borrowed '%s'. Due date: %s. Please return on time!",
                    book.getTitle(),
                    loan.getDueDate().toLocalDate());

        } catch (Exception e) {
            return "Error: Failed to borrow book. Please try again.";
        }
    }

    // US-05: Get user's overdue books list
    public List<String> getUserOverdueBooks(Integer userId) {
        List<String> overdueBooks = new ArrayList<>();

        try {
            List<Loan> allLoans = loanRepository.findAll();
            LocalDateTime now = LocalDateTime.now();

            for (Loan loan : allLoans) {
                if (loan.getUserID() != null &&
                        loan.getUserID().equals(userId) &&
                        loan.getReturnDate() == null &&
                        loan.getDueDate() != null &&
                        loan.getDueDate().isBefore(now)) {

                    long daysOverdue = java.time.temporal.ChronoUnit.DAYS.between(
                            loan.getDueDate().toLocalDate(),
                            now.toLocalDate()
                    );

                    overdueBooks.add(loan.getBookTitle() + " (Overdue by " + daysOverdue + " days)");
                }
            }
        } catch (Exception e) {
            // If error, return empty list
        }

        return overdueBooks;
    }


    }