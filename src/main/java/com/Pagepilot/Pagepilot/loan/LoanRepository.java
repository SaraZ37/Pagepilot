package com.Pagepilot.Pagepilot.loan;

import com.Pagepilot.Pagepilot.book.Book;
import com.Pagepilot.Pagepilot.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Integer> {
    List<Loan> findByUser(User user);

    List<Loan> findByReturnDateIsNull();
}
