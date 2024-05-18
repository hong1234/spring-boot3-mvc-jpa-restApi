package com.hong.demo.repository;

// import org.springframework.data.repository.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.hong.demo.domain.UserAccount;

public interface UserRepository extends JpaRepository<UserAccount, Long> {
	UserAccount findByUsername(String username);
}
