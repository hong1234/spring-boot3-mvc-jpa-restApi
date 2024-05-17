package com.hong.demo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import jakarta.persistence.*;
// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.Lob;
// import jakarta.persistence.ManyToOne;
// import jakarta.persistence.Table;
// import jakarta.persistence.Temporal;
// import jakarta.persistence.TemporalType;
// import jakarta.persistence.Enumerated

// import jakarta.validation.constraints.*;
// import jakarta.validation.constraints.NotNull;
// import jakarta.validation.constraints.NotBlank;
// import jakarta.validation.constraints.Email;
// import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
// import com.fasterxml.jackson.annotation.JsonFormat;

// import com.hong.demo.validation.EnumNamePattern;

@Data
@Entity
@Table(name = "reviews")
public class Review {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    // @NotNull
    // @Size(min = 3, max = 50, message = "must be minimum 3 characters, and maximum 50 characters long")
    @Column(name = "name", nullable = false, length = 150)
    private String name;

    // @NotNull
    // @Email(message="Please provide a valid email")
    @Column(name = "email", nullable = false, length = 150)
    private String email;

    // @Lob
    // @NotNull
    // @Size(min = 3, max = 500, message = "must be minimum 3 characters, and maximum 500 characters long")
    @Column(name = "content", nullable = false, columnDefinition="TEXT")
    private String content;

    // @NotNull
    // @EnumNamePattern(regexp = "Low|Medium|High")
    @Enumerated(EnumType.STRING)
    @Column(name = "like_status")
    private LikeStatus likeStatus;

    // @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Europe/Berlin")
    @Column(name="created_on", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdOn = LocalDateTime.now();

    // @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Europe/Berlin")
    @Column(name="updated_on", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedOn;
    
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="book_id")
    private Book book;

}
