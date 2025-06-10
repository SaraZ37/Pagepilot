package com.Pagepilot.Pagepilot.loan;

import com.Pagepilot.Pagepilot.user.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LoanService {

    private final LoanRepository loanRepository;

    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
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
}
