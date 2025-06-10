package com.Pagepilot.Pagepilot.loan;

import com.Pagepilot.Pagepilot.user.User;
import com.Pagepilot.Pagepilot.user.UserRepository;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService, UserRepository userRepository) {
        this.loanService = loanService;
    }

    @GetMapping
    public List<Loan> getAllLoans(){
        return loanService.getAllLoans();
    }



}