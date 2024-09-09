package com.springkafka.demo.test.service;


import com.springkafka.demo.main.SpringKafkaApplication;
import com.springkafka.demo.main.dto.Book;
import com.springkafka.demo.main.service.BookService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test cases for BookService
 * Implementation of the {@link BookService}
 *
 * 1. New book record & get all Book records
 * 2. Get book record
 * 3. Update book record
 * 4. Delete book record
 *
 */
@SpringBootTest( classes = SpringKafkaApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Test
    @Order(1)
    public void newBookRecordAndAllBookList(){

        assertThat(bookService.
                createBookRecord(new Book("Java Fundamental", "John Doe")))
                .isTrue();
        assertThat(bookService.
                createBookRecord(new Book("Java APIs", "John Smith")))
                .isTrue();

        assertThat(bookService.getAllBooks().size()).isEqualTo(2);
    }

    @Test
    @Order(2)
    public void getBookDetailByBookId(){
        assertThat(bookService.
                createBookRecord(new Book("Java Spring", "John Doe")))
                .isTrue();

        Book bookDetail = bookService.getBooksByBookId(3);
        assertThat(bookDetail).isNotNull();
        assertThat(bookDetail.getTitle()).isEqualTo("Java Spring");
        assertThat(bookDetail.getAuthor()).isEqualTo("John Doe");

        assertThat(bookService.getBooksByBookId(4)).isNull();
    }

    @Test
    @Order(3)
    public void updateBookDetailByBookId(){
        assertThat(bookService.
                createBookRecord(new Book("Java Fundamental", "John Doe")))
                .isTrue();

        assertThat(bookService.updateBooksByBookId(4, new Book("Java Fundamental Updated", "John Smith"))).isTrue();
        Book bookDetail = bookService.getBooksByBookId(4);
        assertThat(bookDetail.getTitle()).isEqualTo("Java Fundamental Updated");
        assertThat(bookDetail.getAuthor()).isEqualTo("John Smith");
    }


    @Test
    @Order(4)
    public void DeleteBookDetailByBookId(){
        assertThat(bookService.
                createBookRecord(new Book("Java Fundamental", "John Doe")))
                .isTrue();
        assertThat(bookService.
                createBookRecord(new Book("Java APIs", "John Smith")))
                .isTrue();
        assertThat(bookService.
                createBookRecord(new Book("Java Spring", "David")))
                .isTrue();

        assertThat(bookService.getAllBooks().size()).isEqualTo(7);

        assertThat(bookService.deleteBooksByBookId(1)).isTrue();
        assertThat(bookService.getAllBooks().size()).isEqualTo(6);
    }
}
