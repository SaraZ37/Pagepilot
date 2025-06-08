package com.Pagepilot.Pagepilot.user;

import com.Pagepilot.Pagepilot.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
