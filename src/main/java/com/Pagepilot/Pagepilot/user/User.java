package com.Pagepilot.Pagepilot.user;

import com.Pagepilot.Pagepilot.loan.Loan;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer userId;

    @Getter
    @Column(name = "username")
    private String username;

    @Getter
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user")
    private Set<Loan> loans;

    public User(Integer userId, String username, String email, String password, Set<Loan> loans) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.loans = loans;
    }

    public User() {

    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", loans=" + loans +
                '}';
    }
}
