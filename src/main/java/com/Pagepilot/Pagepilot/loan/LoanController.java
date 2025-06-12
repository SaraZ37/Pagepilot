package com.Pagepilot.Pagepilot.loan;

import com.Pagepilot.Pagepilot.book.BookRepository;
import com.Pagepilot.Pagepilot.book.BookService;
import com.Pagepilot.Pagepilot.user.User;
import com.Pagepilot.Pagepilot.user.UserRepository;
import com.Pagepilot.Pagepilot.user.UserService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/loans")
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





}