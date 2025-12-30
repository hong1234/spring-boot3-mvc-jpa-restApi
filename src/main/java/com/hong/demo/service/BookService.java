package com.hong.demo.service;

import java.util.List;
// import java.util.Optional;
// import java.util.Date;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
// import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hong.demo.domain.Book;
import com.hong.demo.domain.Review;
import com.hong.demo.repository.BookRepository;
import com.hong.demo.repository.ReviewRepository;
import com.hong.demo.exceptions.ResourceNotFoundException;
import com.hong.demo.exceptions.BookServiceException;
import com.hong.demo.exceptions.ErrorDetails;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;

    // books ---

    public Iterable<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Iterable<Book> searchBooksByTitle(String title) {
        return bookRepository.searchByTitle(title);
    }

    public Book getBookById(Integer bookId) throws BookServiceException { // throws Exception
        // Book storedBook = bookRepository.findById(bookId)
        // .orElseThrow(() -> new ResourceNotFoundException("Book with Id="+bookId+" not found")); 
        // return storedBook; 
        return bookRepository.findById(bookId).get();
    }

    public void deleteBook(Integer bookId) throws BookServiceException {
        Book book =  bookRepository.findById(bookId).get();
        bookRepository.deleteById(book.getId());
    }

    public Book storeBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Integer bookId, Book book) throws BookServiceException {
        Book storedBook = bookRepository.findById(bookId).get();
        storedBook.setTitle(book.getTitle());
        storedBook.setContent(book.getContent());
        storedBook.setUpdatedOn(LocalDateTime.now());
        return bookRepository.save(storedBook);
    }

    // reviews ---

    public List<Review> getBookReviews(Integer bookId) throws BookServiceException {
        return reviewRepository.getReviewsOfBook(bookId);
    }

    public Review addReviewToBook(Integer bookId, Review review) throws BookServiceException {
        Book book = bookRepository.findById(bookId).get();
        review.setBook(book);
        return reviewRepository.save(review);
    }

    public void deleteReview(Integer reviewId) throws BookServiceException {
        // Review review = reviewRepository.findById(reviewId)
        // .orElseThrow(() -> new ResourceNotFoundException("Review with Id="+reviewId+" not found"));
        Review review = reviewRepository.findById(reviewId).get();
        reviewRepository.deleteById(review.getId());
    }

}
