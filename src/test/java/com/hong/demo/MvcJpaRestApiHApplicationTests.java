package com.hong.demo;

import java.util.Arrays;
import java.util.List;
// import java.util.Date;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.apache.commons.codec.binary.Base64;
import static org.assertj.core.api.Assertions.assertThat;
// import static org.assertj.core.api.Assertions.*; // https://www.baeldung.com/introduction-to-assertj

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;

import com.hong.demo.domain.Book;
import com.hong.demo.exceptions.ErrorDetails;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MvcJpaRestApiHApplicationTests { 

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	/*
    * Add HTTP Authorization header, using Basic-Authentication to send user-credentials.
    */
	private static HttpHeaders getHeaders(){
    	String plainCredentials="admin:admin";
    	String base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));
    	
    	HttpHeaders headers = new HttpHeaders();
        //headers.setContentType(MediaType.APPLICATION_JSON);
    	headers.add("Authorization", "Basic " + base64Credentials);
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	return headers;
    }
	
	private String getRootUrl(){
		return "http://localhost:"+port;
	}
	
	@Test
	public void testGetAllBooks(){	
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        ResponseEntity<Book[]> response = restTemplate.exchange(getRootUrl()+"/api/books", HttpMethod.GET, request, Book[].class);
        // List<Book> books = Arrays.asList(response.getBody());
        // assertNotNull(books);
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
		assertThat(response.getBody()).hasSizeGreaterThan(0);
	}
        
   
	// @Test
	// public void testGetBookById(){
	// 	HttpEntity<String> request = new HttpEntity<String>(getHeaders());
    //     ResponseEntity<Book> response = restTemplate.exchange(getRootUrl()+"/api/books/1", HttpMethod.GET, request, Book.class);
    //     assertNotNull(response.getBody());
	// }

       
	@Test
	public void testCreateBook(){
		Book book = new Book();
		book.setTitle("Exploring SpringBoot REST");
		book.setContent("SpringBoot is awesome!!");
		book.setCreatedOn(LocalDateTime.now());
		
        HttpEntity<Object> request = new HttpEntity<Object>(book, getHeaders());
        ResponseEntity<Book> bookResponse = restTemplate.exchange(getRootUrl()+"/api/books", HttpMethod.POST, request, Book.class);
        assertThat(bookResponse.getStatusCodeValue()).isEqualTo(200);
		// assertNotNull(bookResponse);
        // assertNotNull(bookResponse.getBody());	
	}

	
	// @Test
	// public void testUpdateBook(){
	// 	int id = 1;
    //     Book book = new Book();
    //     book.setId(id);
    //     book.setTitle("UPDATE");
	// 	book.setContent("UPDATE");
	// 	book.setCreatedOn(LocalDateTime.now());

    //     HttpEntity<Object> request = new HttpEntity<Object>(book, getHeaders());
	// 	ResponseEntity<Book> response = restTemplate.exchange(getRootUrl()+"/api/books/"+id, HttpMethod.PUT, request, Book.class);
    //     assertNotNull(response.getBody());
	// }
        
	@Test
	public void testDeleteBook(){
		int id = 2;
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl()+"/api/books/"+id, HttpMethod.DELETE, request, String.class);
        // assertNull(response.getBody());
		assertThat(response.getBody()).hasSize(0); //.hasSize(0);
	}

}
