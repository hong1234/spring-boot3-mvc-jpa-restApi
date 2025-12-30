package com.hong.demo.repository;

// import org.springframework.data.repository.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.hong.demo.domain.UserAccount;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserAccount, Integer> {
	// UserAccount findByUsername(String username);
	Optional<UserAccount> findByUsername(String username);
}
