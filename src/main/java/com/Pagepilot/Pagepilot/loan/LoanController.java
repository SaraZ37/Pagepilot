package com.Pagepilot.Pagepilot.loan;

import com.Pagepilot.Pagepilot.book.BookRepository;
import com.Pagepilot.Pagepilot.book.BookService;
import com.Pagepilot.Pagepilot.user.User;
import com.Pagepilot.Pagepilot.user.UserRepository;
import com.Pagepilot.Pagepilot.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/loans")@Tag(name = "Loan Management", description = "APIs for borrowing and returning books")

public class LoanController {

    private final LoanService loanService;
    public LoanController(LoanService loanService) {

        this.loanService = loanService;
    }

    @GetMapping
    public List<Loan> getAllLoans(){
        return loanService.getAllLoans();
    }


    @PostMapping
    public ResponseEntity<Loan> createLoan(@RequestBody LoanRequestDto loanRequestDto) {
        Loan createdLoan = loanService.createNewLoan(loanRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLoan);
    }

    // US-05: Borrow book with validations (GET request for easy browser testing)
    @GetMapping("/borrow/{userId}/{bookId}")
    public String borrowBook(@PathVariable Integer userId, @PathVariable Integer bookId) {
        return loanService.borrowBook(userId, bookId);
    }

    // US-05: Check if user can borrow (no overdue books)
    @GetMapping("/check-user/{userId}")
    public String checkUserBorrowingStatus(@PathVariable Integer userId) {
        if (loanService.userHasOverdueBooks(userId)) {
            List<String> overdueBooks = loanService.getUserOverdueBooks(userId);
            return "Cannot borrow: You have " + overdueBooks.size() + " overdue books - " +
                    String.join(", ", overdueBooks);
        } else {
            return "You can borrow books";
        }
    }

    // US-05: Check book availability
    @GetMapping("/check-book/{bookId}")
    public String checkBookAvailability(@PathVariable Integer bookId) {
        if (loanService.isBookCurrentlyBorrowed(bookId)) {
            return "Borrowed - " + loanService.getBookAvailabilityDate(bookId);
        } else {
            return "Available for borrowing";
        }
    }

    // US-05: Get user's overdue books
    @GetMapping("/overdue/{userId}")
    public List<String> getOverdueBooks(@PathVariable Integer userId) {
        return loanService.getUserOverdueBooks(userId);
    }
}