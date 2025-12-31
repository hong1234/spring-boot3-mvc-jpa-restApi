package com.hong.demo.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;

import lombok.*;

// import lombok.AllArgsConstructor;
// import lombok.Builder;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;
// import lombok.ToString;

// @Getter
// @Setter
// @AllArgsConstructor
// @NoArgsConstructor
// @Builder
// @ToString

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserAccount {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) 
  private Integer id;

  private String username;
  private String password;
  private String authority;

  public UserAccount(String username, String password, String authority) {
    this.username = username;
    this.password = password;
    this.authority = authority;
  }

  public UserDetails asUser() {
    return User.builder()
      .username(getUsername())
      .password(getPassword())
      .authorities(List.of(new SimpleGrantedAuthority(getAuthority())))
      .build();
  }

}

//////////////////

// package com.hong.demo.domain;

// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.List;
// import java.util.Objects;

// import jakarta.persistence.*;

// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;

// @Entity
// public class UserAccount {

//   @Id
//   @GeneratedValue 
//   private int id;
//   private String username;
//   private String password;
//   private String authority;

//   protected UserAccount() {}

//   public UserAccount(String username, String password, String authority) {
//     this.username = username;
//     this.password = password;
//     this.authority = authority;
//   }

//   public int getId() {
//     return id;
//   }

//   public void setId(int id) {
//     this.id = id;
//   }

//   public String getUsername() {
//     return username;
//   }

//   public void setUsername(String username) {
//     this.username = username;
//   }

//   public String getPassword() {
//     return password;
//   }

//   public void setPassword(String password) {
//     this.password = password;
//   }

//   public String getAuthority() {
//     return authority;
//   }

//   public void setAuthority(String authority) {
//     this.authority = authority;
//   }

//   @Override
//   public boolean equals(Object o) {
//     if (this == o)
//       return true;
//     if (o == null || getClass() != o.getClass())
//       return false;
//     UserAccount user = (UserAccount) o;
//     return Objects.equals(id, user.id) && Objects.equals(username, user.username)
//       && Objects.equals(password, user.password) && Objects.equals(authority, user.authority);
//   }

//   @Override
//   public int hashCode() {
//     return Objects.hash(id, username, password, authority);
//   }

//   @Override
//   public String toString() {
//     return "User{" + "id=" + id + ", username='" + username + '\'' + ", password='" + password + '\'' + ", authority="
//       + authority + '}';
//   }

//   public UserDetails asUser() {
//     return User.builder()
//       .username(getUsername())
//       .password(getPassword())
//       .authorities(List.of(new SimpleGrantedAuthority(getAuthority())))
//       .build();
//   }

// }

//////////////////////

// package com.hong.demo.domain;

// import jakarta.persistence.ElementCollection;
// import jakarta.persistence.Entity;
// import jakarta.persistence.FetchType;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.Id;

// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.List;
// import java.util.Objects;

// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;

// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;

// import org.springframework.security.crypto.password.PasswordEncoder;

// @Entity
// public class UserAccount {

//   @Id
//   @GeneratedValue 
//   private Long id;

//   private String username;
//   private String password;

//   @ElementCollection(fetch = FetchType.EAGER)
//   private List<GrantedAuthority> authorities = new ArrayList<>();

//   protected UserAccount() {}

//   public UserAccount(String username, String password, String... authorities) {
//     this.username = username;
//     this.password = password;
//     this.authorities = Arrays.stream(authorities) 
//       .map(SimpleGrantedAuthority::new) 
//       .map(GrantedAuthority.class::cast)
//       .toList();
//   }

//   public UserDetails asUser() {
//     // User.UserBuilder builder = User.builder().passwordEncoder(passwordEncoder::encode);
//     // return builder
//     //   .username(getUsername())
//     //   .password(getPassword())
//     //   .authorities(getAuthorities())
//     //   .build();

//     return User
//             .withUsername(getUsername())
//             .password(getPassword())
//             .authorities(getAuthorities())
//             .build();
//   }

//   public Long getId() {
//     return id;
//   }

//   public void setId(Long id) {
//     this.id = id;
//   }

//   public String getUsername() {
//     return username;
//   }

//   public void setUsername(String username) {
//     this.username = username;
//   }

//   public String getPassword() {
//     return password;
//   }

//   public void setPassword(String password) {
//     this.password = password;
//   }

//   public List<GrantedAuthority> getAuthorities() {
//     if (authorities == null) {
//       this.authorities = new ArrayList<>();
//     }
//     return authorities;
//   }

//   public void setAuthorities(List<GrantedAuthority> authorities) {
//     this.authorities = authorities;
//   }

//   @Override
//   public boolean equals(Object o) {
//     if (this == o)
//       return true;
//     if (o == null || getClass() != o.getClass())
//       return false;
//     UserAccount user = (UserAccount) o;
//     return Objects.equals(id, user.id) && Objects.equals(username, user.username)
//       && Objects.equals(password, user.password) && Objects.equals(authorities, user.authorities);
//   }

//   @Override
//   public int hashCode() {
//     return Objects.hash(id, username, password, authorities);
//   }

//   @Override
//   public String toString() {
//     return "User{" + "id=" + id + ", username='" + username + '\'' + ", password='" + password + '\'' + ", authorities="
//       + authorities + '}';
//   }
// }
