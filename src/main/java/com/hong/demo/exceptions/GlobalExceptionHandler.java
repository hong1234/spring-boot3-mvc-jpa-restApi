package com.hong.demo.exceptions;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.time.LocalDateTime;

import org.springframework.validation.FieldError;

// import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.MethodArgumentNotValidException;

// import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.context.request.WebRequest;

// @Slf4j 
// @ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)  // thrown by HttpMessageConverter implementation
    public ErrorDetails bindingException(HttpMessageNotReadableException e) { 
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setStatus(HttpStatus.BAD_REQUEST);
        errorDetails.setMessage(e.getMessage());
        return errorDetails;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(HttpMessageNotWritableException.class)  // thrown by HttpMessageConverter implementation
    public ErrorDetails bindingResponException(HttpMessageNotWritableException e) { 
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        errorDetails.setMessage(e.getMessage());
        return errorDetails;
    }

    // @ResponseStatus(HttpStatus.BAD_REQUEST)
    // @ExceptionHandler(MethodArgumentNotValidException.class)
    // public ErrorDetails handleValidationExceptions(MethodArgumentNotValidException ex) {
    //     Map<String, String> errors = new HashMap<>();
    //     ex.getBindingResult().getAllErrors().forEach((error) -> {
    //         String fieldName = ((FieldError) error).getField();
    //         String errorMessage = error.getDefaultMessage();
    //         errors.put(fieldName, errorMessage);
    //     });

    //     ErrorDetails errorDetails = new ErrorDetails();
    //     errorDetails.setStatus(HttpStatus.BAD_REQUEST);
    //     errorDetails.setErrorDetails(errors);
    //     return errorDetails;
    // }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ErrorDetails handlerNotFoundException(NoHandlerFoundException e) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setStatus(HttpStatus.NOT_FOUND);
        errorDetails.setMessage(e.getMessage());
        // errorDetails.setMessage("URL is not correct");
        return errorDetails;
    }

    // @ExceptionHandler({AuthenticationException.class})
    // @ResponseStatus(HttpStatus.UNAUTHORIZED)
    // public ErrorDetails handleAuthenticationException(AuthenticationException e) {
    //     ErrorDetails errorDetails = new ErrorDetails();
    //     errorDetails.setStatus(HttpStatus.UNAUTHORIZED);
    //     errorDetails.setMessage(e.getMessage());
    //     return errorDetails;
    // }

    @ExceptionHandler({AuthenticationException.class, UsernameNotFoundException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDetails handleAuthenticationException(Exception e) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setStatus(HttpStatus.UNAUTHORIZED);
        errorDetails.setMessage(e.getMessage());
        return errorDetails;
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorDetails forbiddenException(AccessDeniedException e) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setStatus(HttpStatus.FORBIDDEN);
        errorDetails.setMessage(e.getMessage());
        return errorDetails;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorDetails resourceNotFoundException(ResourceNotFoundException e) {
        ErrorDetails errorDetails = new ErrorDetails();
        // errorDetails.setStatus(HttpStatus.NOT_FOUND);
        errorDetails.setMessage(e.getMessage());
        return errorDetails;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public ErrorDetails handNoSuchElementException(NoSuchElementException e) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setStatus(HttpStatus.NOT_FOUND);
        // errorDetails.setMessage(e.getMessage());
        errorDetails.setMessage("the element not found");
        return errorDetails;
    }

    // @ResponseStatus(HttpStatus.NOT_FOUND)
    // @ExceptionHandler(NoResourceFoundException.class)
    // public ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex) {
    //     Map<String, Object> body = new HashMap<>();
    //     body.put("message", "Resource not found");
    //     body.put("timestamp", LocalDateTime.now());
    //     return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    // }

    // @ResponseStatus(HttpStatus.NOT_FOUND)
    // @ExceptionHandler(NoSuchElementException.class)
    // public ResponseEntity<Object> handleNotFoundException(NoSuchElementException ex) {
    //     Map<String, Object> body = new HashMap<>();
    //     // body.put("message", "Book not found");
    //     body.put("message", ex.getMessage());
    //     body.put("timestamp", LocalDateTime.now());
    //     return new ResponseEntity<>(body, HttpStatus.NOT_FOUND); 
    // }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ErrorDetails validationException(ValidationException e) {
        ErrorDetails errorDetails = new ErrorDetails();
        // errorDetails.setStatus(HttpStatus.BAD_REQUEST);
        errorDetails.setMessage(e.getMessage());
        return errorDetails;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        List<String> errors = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(FieldError::getDefaultMessage)
        .toList();

        // Map<String, Object> body = new HashMap<>();
        // // body.put("message", ex.getMessage());
        // body.put("message", errors);
        // body.put("timestamp", LocalDateTime.now());
        // return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);

        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setStatus(HttpStatus.BAD_REQUEST);
        errorDetails.setMessage(errors);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    // @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    // @ExceptionHandler
    // public ErrorDetails otherExceptions(Exception e) {
    //     ErrorDetails errorDetails = new ErrorDetails();
    //     errorDetails.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    //     errorDetails.setMessage(e.getMessage());
    //     return errorDetails;
    // }

    // @ExceptionHandler(SpringAppException.class)
    // public ErrorDetails servletRequestBindingException(SpringAppException e) {
    //     // log.error("SpringBlogException occurred: " + e.getMessage());
    //     ErrorDetails errorDetails = new ErrorDetails();
    //     errorDetails.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    //     errorDetails.setMessage(e.getMessage());
    //     return errorDetails;
    // }

    // @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    // protected ResponseEntity<Void> handleException(Exception ex) {
    //     return ResponseEntity.internalServerError().build();
    // }

}
