package com.hong.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.http.HttpMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import javax.sql.DataSource;

import com.hong.demo.domain.UserAccount;
import com.hong.demo.repository.UserRepository;
import com.hong.demo.repository.UserManagementRepository;

import org.springframework.boot.CommandLineRunner;

// import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class SecurityConfig {

    @Autowired
    @Qualifier("delegatedAuthenticationEntryPoint")
    AuthenticationEntryPoint authEntryPoint;

    @Autowired
    @Qualifier("customAccessDeniedHandler")
    private AccessDeniedHandler accessDeniedHandler;

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // @Bean
    // public UserDetailsService userDetailsService(){
    //     UserDetails hong = User.builder()
    //             .username("hong")
    //             .password(passwordEncoder().encode("password"))
    //             .roles("USER")
    //             .build();

    //     UserDetails admin = User.builder()
    //             .username("admin")
    //             .password(passwordEncoder().encode("admin"))
    //             .roles("ADMIN")
    //             .build();

    //     return new InMemoryUserDetailsManager(hong, admin); 
    // }

    // @Bean
    // UserDetailsService users(DataSource dataSource) {
    //     // return new JdbcUserDetailsManager(dataSource);

    //     User.UserBuilder builder  = User.builder().passwordEncoder(passwordEncoder()::encode);
    //     var hong  = builder.username("hong").password("password").roles("USER").build();
    //     var admin = builder.username("admin").password("admin").roles("ADMIN").build();
    //     var boss  = builder.username("bigboss").password("bigboss").roles("USER", "ADMIN").build();

    //     var manager = new JdbcUserDetailsManager(dataSource);
    //     manager.createUser(hong);
    //     manager.createUser(admin);
    //     manager.createUser(boss);

    //     return manager;
    // }

    @Bean
    CommandLineRunner initUsers(UserRepository repository) {
        return args -> {
            repository.save(new UserAccount("hong", "hong", "ROLE_USER"));
            repository.save(new UserAccount("autor", "autor", "ROLE_AUTOR"));
            repository.save(new UserAccount("admin", "admin", "ROLE_USER", "ROLE_AUTOR", "ROLE_ADMIN"));
        };
    } 

    // @Bean
    // CommandLineRunner initUsers(UserManagementRepository repository) {
    //     return args -> {
    //         repository.save(new UserAccount("hong", "hong", "ROLE_USER"));
    //         repository.save(new UserAccount("autor", "autor", "ROLE_AUTOR"));
    //         repository.save(new UserAccount("admin", "admin", "ROLE_USER", "ROLE_AUTOR", "ROLE_ADMIN"));
    //     };
    // } 

    @Bean
    UserDetailsService userService(UserRepository repo) {
        // UsernameNotFoundException - if the user could not be found or the user has no GrantedAuthority
	    // return username -> repo.findByUsername(username).asUser(passwordEncoder());

        return username -> {
            UserAccount acc = repo.findByUsername(username);
            if (acc != null) 
                return acc.asUser(passwordEncoder());
            throw new UsernameNotFoundException(username + " not found");
        };
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf((csrf) -> csrf.disable())
            .authorizeHttpRequests(authorize -> authorize
                // .anyRequest().authenticated()
                // .requestMatchers(HttpMethod.GET, "/api/books/**").hasRole("USER")
                .requestMatchers(HttpMethod.GET).permitAll()

                // .requestMatchers(HttpMethod.POST, "/api/books").hasRole("AUTOR")
                // .requestMatchers(HttpMethod.PUT, "/api/books/{bookId}").hasRole("AUTOR")
                // .requestMatchers(HttpMethod.DELETE, "/api/books/{bookId}").hasRole("AUTOR")

                .requestMatchers("/api/books/**").hasRole("AUTOR")

                // .requestMatchers(HttpMethod.DELETE, "/api/books/**").hasRole("ADMIN")

                // .requestMatchers(HttpMethod.POST, "/api/reviews/{bookId}").hasRole("USER")
                // .requestMatchers(HttpMethod.PUT, "/api/reviews/{reviewId}").hasRole("USER")
                // .requestMatchers(HttpMethod.DELETE, "/api/reviews/{reviewId}").hasRole("USER")
                .requestMatchers("/api/reviews/**").hasRole("USER")

                .anyRequest().denyAll()
            )
            // .httpBasic(Customizer.withDefaults());

            .httpBasic(basic -> basic.authenticationEntryPoint(authEntryPoint))
            .exceptionHandling(customizer -> customizer.accessDeniedHandler(accessDeniedHandler))
            ;

        return http.build();
    }

}
