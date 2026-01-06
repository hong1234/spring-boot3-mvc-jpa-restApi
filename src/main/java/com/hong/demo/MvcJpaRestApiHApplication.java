package com.hong.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;

// import org.springframework.context.ConfigurableApplicationContext;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.hong.demo.domain.UserAccount;
import com.hong.demo.repository.UserRepository;
import com.hong.demo.repository.UserManagementRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@SpringBootApplication
public class MvcJpaRestApiHApplication implements CommandLineRunner { 

	// private final BookJPARepository bookJPARepository;
	private final UserManagementRepository repository;
    private final PasswordEncoder passwordEncoder;

	// public MvcJpaRestApiHApplication(BookJPARepository bookJPARepository,  
    //     UserManagementRepository repository, PasswordEncoder passwordEncoder) {
    //     this.bookJPARepository = bookJPARepository;
    //     this.repository = repository; 
    //     this.passwordEncoder = passwordEncoder;
    // }

	public static void main(String[] args) {
		SpringApplication.run(MvcJpaRestApiHApplication.class, args);
	}

	@Override
    public void run(String... args) throws Exception {
		// repository.save(new UserAccount("user", passwordEncoder.encode("user"), "ROLE_USER"));
        // repository.save(new UserAccount("autor", passwordEncoder.encode("autor"), "ROLE_AUTOR"));
        // repository.save(new UserAccount("admin", passwordEncoder.encode("admin"), "ROLE_ADMIN"));
	}

}
