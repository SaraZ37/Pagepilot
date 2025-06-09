package com.Pagepilot.Pagepilot.user;

import com.Pagepilot.Pagepilot.loan.Loan;
import com.Pagepilot.Pagepilot.loan.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;
    private final LoanService loanService;

    public UserController(UserService userService, LoanService loanService) {

        this.userService = userService;
        this.loanService = loanService;
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}/loans")
    public ResponseEntity<?> getUserLoans(@PathVariable String userId) {
        User user = userService.findById(userId);
        List<Loan> loansHistory = loanService.getPastUserLoans(user);
        List<Loan> currentLoans = loanService.getCurrentUserLoans(user);
        if (currentLoans.isEmpty() && loansHistory.isEmpty()) {
            return ResponseEntity.ok("Your loan history is empty");
        }
        if (loansHistory.isEmpty() && !currentLoans.isEmpty()) {
            return ResponseEntity.ok("Current loans: " + currentLoans);
        }
        if (currentLoans.isEmpty() && !loansHistory.isEmpty()) {
            return ResponseEntity.ok("Loans history: " + loansHistory);
        }
        return ResponseEntity.ok("Loans history: " + loansHistory + "\nCurrent loans: " + currentLoans);
    }
    /*@GetMapping("/{userId}/loans")
    public ResponseEntity<Map<String, Object>> getUserLoans(@PathVariable String userId) {
        User user = userService.findById(userId);
        List<Loan> currentLoans = loanService.getCurrentUserLoans(user);
        List<Loan> loansHistory = loanService.getPastUserLoans(user);

        Map<String, Object> result = new HashMap<>();
        result.put("currentLoans", currentLoans);
        result.put("loansHistory", loansHistory);
        return ResponseEntity.ok(result);
    }*/

}
