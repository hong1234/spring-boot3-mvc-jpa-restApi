package com.hong.demo.domain;

import lombok.Data;
// import lombok.NoArgsConstructor;

// import java.util.Date; // The @Temporal is a Hibernate JPA annotation that is used for java.util.Date or java.util.Calendar
import java.util.List;

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

import com.fasterxml.jackson.annotation.JsonManagedReference;
// import com.fasterxml.jackson.annotation.JsonFormat;
// import com.fasterxml.jackson.annotation.JsonIgnore;

// import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Data
@Entity
@Table(name = "books")
public class Book {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;
    
    @NotNull
    @Size(min = 3, max = 50, message = "must be minimum 3 characters, and maximum 50 characters long")
    @Column(name = "title", nullable = false, length = 150)
    private String title;

    // @Lob
    @NotNull
    @Size(min = 3, max = 500, message = "must be minimum 3 characters, and maximum 500 characters long")
    @Column(name = "content", nullable = false, columnDefinition="TEXT")
    private String content;

    // @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Europe/Berlin")
    @Column(name="created_on", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdOn = LocalDateTime.now();

    // @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Europe/Berlin")
    @Column(name="updated_on", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedOn;
        
    @JsonManagedReference
    @OneToMany(mappedBy="book", cascade=CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;

}
