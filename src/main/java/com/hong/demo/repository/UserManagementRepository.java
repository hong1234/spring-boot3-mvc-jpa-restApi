package com.hong.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hong.demo.domain.UserAccount;

public interface UserManagementRepository extends JpaRepository<UserAccount, Long> {}