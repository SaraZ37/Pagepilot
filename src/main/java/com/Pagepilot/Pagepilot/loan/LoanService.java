package com.Pagepilot.Pagepilot.loan;

import com.Pagepilot.Pagepilot.book.Book;
import com.Pagepilot.Pagepilot.book.BookRepository;
import com.Pagepilot.Pagepilot.user.User;
import com.Pagepilot.Pagepilot.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

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
        }else {
            throw new IllegalStateException("Book is not available for loan.");
        }

    }
}


