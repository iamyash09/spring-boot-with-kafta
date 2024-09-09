package com.springkafka.demo.main.controller;

import com.google.common.base.Strings;
import com.springkafka.demo.main.dto.Book;
import com.springkafka.demo.main.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Endpoints for the Book entity
 *
 */
@RestController
@RequestMapping("/rest/api")
public class RestApiController {

    @Autowired
    private BookService bookService;

    /**
     * Retrieve all books
     *
     * @return retrieve list of book
     */
    @GetMapping(value = "/books")
    public ResponseEntity<Map<Integer, Book>> getAllBooks()
    {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    /**
     * Retrieve a book by its ID
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/books/{id}")
    public ResponseEntity<?> getBooksByBookId(@PathVariable("id") int id)
    {
        // Check valid input
        checkArgument(id > 0);

        Book bookDetail = bookService.getBooksByBookId(id);
        if( bookDetail != null ) {
            return ResponseEntity.ok(bookDetail);
        }
        else {
            return ResponseEntity.internalServerError().body(Map.of("message","Book details not found"));
        }
    }

    /**
     * Create a new book
     * @param bookDTO title, author
     *
     * @return
     */
    @PostMapping(value = "/books")
    public ResponseEntity<Map<String, String>> createBook(@RequestBody Book bookDTO)
    {
        // Check valid input
        checkArgument(!Strings.isNullOrEmpty(bookDTO.getTitle()));
        checkArgument(!Strings.isNullOrEmpty(bookDTO.getAuthor()));

        if( bookService.createBookRecord(bookDTO) ) {

            return ResponseEntity.ok(Map.of("message", "Book details saved"));
        }
        else {
            return ResponseEntity.internalServerError().body(Map.of("message", "Unable to save book details"));
        }
    }

    /**
     * Update book details with book id
     *
     * @param id
     * @param bookDTO title, author
     *
     * @return
     */
    @PutMapping(value = "/books/{id}")
    public ResponseEntity<Map<String, String>> updateBooksByBookId(@PathVariable("id") int id, @RequestBody Book bookDTO)
    {
        // Check valid input
        checkArgument(id > 0);
        checkArgument(!Strings.isNullOrEmpty(bookDTO.getTitle()));
        checkArgument(!Strings.isNullOrEmpty(bookDTO.getAuthor()));

        if( bookService.updateBooksByBookId(id, bookDTO) ) {
            return ResponseEntity.ok(Map.of("message","Book details updated"));
        }
        else {
            return ResponseEntity.internalServerError().body(Map.of("message","Unable to update book details"));
        }
    }

    /**
     * Delete book details by book id
     *
     * @param id
     *
     * @return
     */
    @DeleteMapping(value = "/books/{id}")
    public ResponseEntity<Map<String, String>> deleteBooksByBookId(@PathVariable("id") int id)
    {
        // Check valid input
        checkArgument(id > 0);

        if( bookService.deleteBooksByBookId(id) ) {
            return ResponseEntity.ok(Map.of("message","Book details deleted"));
        }
        else {
            return ResponseEntity.internalServerError().body(Map.of("message","Unable to delete book details"));
        }
    }
	
}
