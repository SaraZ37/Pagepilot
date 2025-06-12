package com.Pagepilot.Pagepilot.user;

import com.Pagepilot.Pagepilot.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional <User> findByUserId(Integer userId);
}
