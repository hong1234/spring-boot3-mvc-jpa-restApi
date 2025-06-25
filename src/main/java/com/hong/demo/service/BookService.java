package com.hong.demo.service;

import com.hong.demo.domain.Book;
import com.hong.demo.domain.Review;
import com.hong.demo.exceptions.ResourceNotFoundException;
import com.hong.demo.repository.BookRepository;
import com.hong.demo.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
//import java.util.Optional;
// import java.util.Date;

import java.time.LocalDateTime;

import com.hong.demo.exceptions.ErrorDetails;

import lombok.AllArgsConstructor;
// import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;

    // books ---

    public Iterable<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Integer bookId) { //throws Exception
        return bookRepository.findById(bookId)
        .orElseThrow(() -> new ResourceNotFoundException("Book with Id="+bookId+" not found"));
    }

    public Iterable<Book> searchBooksByTitle(String title) {
        return bookRepository.searchByTitle(title);
    }

    public Book storeBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Integer bookId, Book book) {
        Book storedBook = bookRepository.findById(bookId)
        .orElseThrow(() -> new ResourceNotFoundException("Book with Id="+bookId+" not found"));
        storedBook.setTitle(book.getTitle());
        storedBook.setContent(book.getContent());
        storedBook.setUpdatedOn(LocalDateTime.now());
        return bookRepository.save(storedBook);
    }

    public void deleteBook(Integer bookId){
        Book book = bookRepository.findById(bookId)
        .orElseThrow(() -> new ResourceNotFoundException("Book with Id="+bookId+" not found"));
        bookRepository.deleteById(bookId);
    }

    // reviews ---

    public List<Review> getBookReviews(Integer bookId) {
        return reviewRepository.getReviewsOfBook(bookId);
    }

    public Review addReviewToBook(Integer bookId, Review review) {
        Book book = bookRepository.findById(bookId)
        .orElseThrow(() -> new ResourceNotFoundException("Book with Id="+bookId+" not found"));
        review.setBook(book);
        // book.getReviews().add(review);
        return reviewRepository.save(review);
    }

    public void deleteReview(Integer reviewId){
        Review review = reviewRepository.findById(reviewId)
        .orElseThrow(() -> new ResourceNotFoundException("Review with Id="+reviewId+" not found"));
        reviewRepository.deleteById(reviewId);
    }

}
