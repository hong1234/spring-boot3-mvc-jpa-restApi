package com.hong.demo.config;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.http.HttpMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// import org.springframework.security.config.Customizer;
import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

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

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
public class WebSecurityConfig {

    // @Autowired
    // @Qualifier("customAuthenticationEntryPoint")
    // private AuthenticationEntryPoint authEntryPoint;

    // @Autowired
    // @Qualifier("customAccessDeniedHandler")
    // private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    @Qualifier("delegatedAuthenticationEntryPoint")
    private AuthenticationEntryPoint authEntryPoint;

    @Autowired
    @Qualifier("delegatedAccessDeniedHandler")
    private AccessDeniedHandler accessDeniedHandler;

    // @Order(1) 
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable())
            .cors(withDefaults())
            // .cors(c -> c.configurationSource(corsConfigurationSource()))
            .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // .securityMatcher("/api/**")
            .authorizeHttpRequests(authorize -> authorize
                // .anyRequest().authenticated()
                .requestMatchers(HttpMethod.GET, "/api/**").authenticated()   
                // .requestMatchers(HttpMethod.GET, "/api/**").permitAll()

                .requestMatchers(HttpMethod.POST, "/api/books/**").hasRole("AUTOR")
                .requestMatchers(HttpMethod.PUT, "/api/books/**").hasRole("AUTOR")
                .requestMatchers(HttpMethod.DELETE, "/api/books/**").hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST, "/api/reviews/**").hasRole("USER")
                .requestMatchers(HttpMethod.PUT, "/api/reviews/**").hasRole("USER")
                .requestMatchers(HttpMethod.DELETE, "/api/reviews/**").hasRole("ADMIN")

                .anyRequest().denyAll()
            )
            // .httpBasic(Customizer.withDefaults());
            .httpBasic(basic -> basic.authenticationEntryPoint(authEntryPoint))
            .exceptionHandling(customizer -> customizer.accessDeniedHandler(accessDeniedHandler))
            ;

        return http.build();
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        // return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner initUsers(UserRepository repository) {
        // return args -> {
        //     repository.save(new UserAccount("user", "user", "ROLE_USER"));
        //     repository.save(new UserAccount("autor", "autor", "ROLE_AUTOR"));
        //     repository.save(new UserAccount("admin", "admin", "ROLE_USER", "ROLE_AUTOR", "ROLE_ADMIN"));
        // };

        return args -> {
            repository.save(new UserAccount("user", passwordEncoder().encode("user"), "ROLE_USER"));
            repository.save(new UserAccount("autor", passwordEncoder().encode("autor"), "ROLE_AUTOR"));
            repository.save(new UserAccount("admin", passwordEncoder().encode("admin"), "ROLE_USER", "ROLE_AUTOR", "ROLE_ADMIN"));
        };
    }

    @Bean
    UserDetailsService userService(UserRepository repo) {
        // UsernameNotFoundException - if the user could not be found or the user has no GrantedAuthority
	    // return username -> repo.findByUsername(username).asUser(passwordEncoder());

        return username -> repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"))
                .asUser();

        // return username -> {
        //     UserAccount acc = repo.findByUsername(username);
        //     if (acc != null) 
        //         return acc.asUser();
        //     throw new UsernameNotFoundException(username + " not found");
        // };
    }

    // @Bean
    // public UserDetailsService userDetailsService(){
    //     UserDetails user = User.builder()
    //             .username("user")
    //             .password(passwordEncoder().encode("userPW"))
    //             .roles("USER")
    //             .build();

    //     UserDetails admin = User.builder()
    //             .username("admin")
    //             .password(passwordEncoder().encode("adminPW"))
    //             .roles("ADMIN")
    //             .build();

    //     return new InMemoryUserDetailsManager(user, admin); 
    // }

    // @Bean
    // UserDetailsService users(DataSource dataSource) {
    //     // return new JdbcUserDetailsManager(dataSource);

    //     User.UserBuilder builder  = User.builder().passwordEncoder(passwordEncoder()::encode);
    //     var user  = builder.username("user").password("user").roles("USER").build();
    //     var autor  = builder.username("autor").password("autor").roles("AUTOR").build();
    //     var admin = builder.username("admin").password("admin").roles("USER", "AUTOR", "ADMIN").build();

    //     var manager = new JdbcUserDetailsManager(dataSource);
    //     manager.createUser(user);
    //     manager.createUser(autor);
    //     manager.createUser(admin);

    //     return manager; 
    // }

    @Bean
	public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();
        // config.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
		config.setAllowedOrigins(Arrays.asList("*"));
		config.setAllowedMethods(Arrays.asList("*"));
		config.setAllowedHeaders(Arrays.asList("*"));
		config.setAllowCredentials(false);
		config.applyPermitDefaultValues();
        
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}

}
