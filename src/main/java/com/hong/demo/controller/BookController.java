package com.hong.demo.controller;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
// import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.bind.annotation.ResponseStatus;
// import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
// import org.springframework.validation.Errors;

import com.hong.demo.domain.ReviewDto;
import com.hong.demo.domain.LikeStatus;
import com.hong.demo.domain.Review;
import com.hong.demo.domain.Book;
import com.hong.demo.repository.ReviewRepository;
import com.hong.demo.repository.BookRepository;

import com.hong.demo.service.BookService;
import com.hong.demo.exceptions.ResourceNotFoundException;
import com.hong.demo.exceptions.ValidationException;
import com.hong.demo.exceptions.ErrorDetails; 
import com.hong.demo.exceptions.BookServiceException;

@RestController
@AllArgsConstructor
@RequestMapping(path=BookController.CONTROLLER_PATH, produces="application/json")
// @CrossOrigin(origins = {"http://localhost:3000", "http://uicloud.com"}) 
public class BookController {

    public static final String CONTROLLER_PATH = "/api/books";

    private final BookService bookService;

    // books -----

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Book> listBooks(){
	    return bookService.getAllBooks();
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Book> searchBooksByTitle(@RequestParam String title){  
        return bookService.searchBooksByTitle(title);
    }

    @GetMapping("/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public Book getBookById(@PathVariable("bookId") Integer bookId) throws BookServiceException {
        return bookService.getBookById(bookId);
    }
    
    // @PostMapping(path="", consumes="application/json")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(
        @RequestBody @Valid  Book book //, 
        // BindingResult result
    ) throws BookServiceException {
        // if(result.hasErrors())
        //     throw new ValidationException(createErrorString(result));
        return bookService.storeBook(book);
        // URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedBook.getId()).toUri();
        // return ResponseEntity.created(location).body(savedBook);
    }

    // @PutMapping(path="/{bookId}", consumes="application/json")
    @PutMapping("/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public Book updateBook(
        @PathVariable("bookId") Integer bookId, 
        @RequestBody @Valid Book book //,
        // BindingResult result
    ) throws BookServiceException { 
    	// if(result.hasErrors())
        //     throw new ValidationException(createErrorString(result));
        return bookService.updateBook(bookId, book);
        // Book updatedBook = bookService.updateBook(bookId, book);
        // URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("").buildAndExpand(updatedBook.getId()).toUri();
        // return ResponseEntity.created(location).body(updatedBook);
    }

    // @PatchMapping(path="/{bookId}", consumes="application/json")
    // @ResponseStatus(HttpStatus.OK)
    // public Book patchBook(@PathVariable("bookId") Integer bookId, @Valid @RequestBody Book patch, Errors errors) {
	//     if (patch.getTitle() != null) {}
    // }

    @DeleteMapping("/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable("bookId") Integer bookId) throws BookServiceException {
        bookService.deleteBook(bookId);
    }

    // reviews -----

    @GetMapping("/{bookId}/reviews")
    @ResponseStatus(HttpStatus.OK)
    public List<Review> getReviewsOfBook(@PathVariable("bookId") Integer bookId) throws BookServiceException {
    	return bookService.getBookReviews(bookId);
    }

    // @PostMapping(path="/{bookId}/reviews", consumes="application/json")
    // @ResponseStatus(HttpStatus.CREATED)
    // public Review createBookReview(@PathVariable("bookId") Integer bookId, @Valid @RequestBody Review review, BindingResult errors){
    //     if(errors.hasErrors())
	//         throw new ValidationException(createErrorString(errors));
    //     return bookService.addReviewToBook(bookId, review);  
    // }

    // @PostMapping(path="/{bookId}/reviews", consumes="application/json")
    @PostMapping("/{bookId}/reviews")
    @ResponseStatus(HttpStatus.CREATED)
    public Review addReviewToBook(
        @PathVariable("bookId") Integer bookId, 
        @RequestBody @Valid ReviewDto reviewDto
    ) throws BookServiceException {
        Review review = reviewDto.asReview();
        return bookService.addReviewToBook(bookId, review);
    }
    
    @DeleteMapping("/reviews/{reviewId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBookReview(@PathVariable("reviewId") Integer reviewId) throws BookServiceException {
        bookService.deleteReview(reviewId);
    }

    // private String createErrorString(BindingResult result) {
    //     StringBuilder sb =  new StringBuilder();
    //     result.getAllErrors().forEach(error -> {
    //         if(error instanceof FieldError) {
    //             FieldError err= (FieldError) error;
    //             sb.append(err.getField()).append(" ").append(err.getDefaultMessage());
    //         }
    //     });
    //     return sb.toString();
    // }
    
    // @ExceptionHandler
    // public ResponseEntity<ErrorDetails> notfoundException(ResourceNotFoundException e) {
    //     ErrorDetails errorDetails = new ErrorDetails();
    //     errorDetails.setStatus(HttpStatus.NOT_FOUND);
    //     errorDetails.setMessage(e.getMessage());
    //     // ResponseEntity(T body, HttpStatusCode status)
    //     return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    // }
    
}
