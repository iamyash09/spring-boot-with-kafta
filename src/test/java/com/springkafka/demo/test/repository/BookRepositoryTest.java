package com.springkafka.demo.test.repository;

import com.springkafka.demo.main.dto.Book;
import com.springkafka.demo.main.repository.BookRepository;
import com.springkafka.demo.main.repository.BookRepositoryImpl;
import org.junit.jupiter.api.*;
import org.springframework.context.annotation.Profile;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test cases for BookRepository
 * Implementation of the {@link BookRepositoryImpl}
 *
 * 1. New book record & get all Book records
 * 2. Get book record
 * 3. Update book record
 * 4. Delete book record
 *
 */
@Profile("Book_Repository")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookRepositoryTest {

    private BookRepositoryImpl bookRepositoryImpl;

    @BeforeEach
    public void before() {
        bookRepositoryImpl = new BookRepositoryImpl();
    }

    @AfterEach
    public void after(){
        bookRepositoryImpl = null;
    }

    @Test
    @Order(1)
    public void newBookRecordAndAllBookList(){
        assertThat(bookRepositoryImpl.
                createBookRecord(new Book("Java Fundamental", "John Doe")))
                .isTrue();
        assertThat(bookRepositoryImpl.
                createBookRecord(new Book("Java APIs", "John Smith")))
                .isTrue();

        assertThat(bookRepositoryImpl.getAllBooks().size()).isEqualTo(2);
    }

    @Test
    @Order(2)
    public void getBookDetailByBookId(){
        assertThat(bookRepositoryImpl.
                createBookRecord(new Book("Java Spring", "John Doe")))
                .isTrue();

        Book bookDetail = bookRepositoryImpl.getBooksByBookId(1);
        assertThat(bookDetail).isNotNull();
        assertThat(bookDetail.getTitle()).isEqualTo("Java Spring");
        assertThat(bookDetail.getAuthor()).isEqualTo("John Doe");

        assertThat(bookRepositoryImpl.getBooksByBookId(2)).isNull();
    }

    @Test
    @Order(3)
    public void updateBookDetailByBookId(){
        assertThat(bookRepositoryImpl.
                createBookRecord(new Book("Java Fundamental", "John Doe")))
                .isTrue();

        assertThat(bookRepositoryImpl.updateBooksByBookId(1, new Book("Java Fundamental Updated", "John Smith"))).isTrue();
        Book bookDetail = bookRepositoryImpl.getBooksByBookId(1);
        assertThat(bookDetail.getTitle()).isEqualTo("Java Fundamental Updated");
        assertThat(bookDetail.getAuthor()).isEqualTo("John Smith");
    }


    @Test
    @Order(4)
    public void DeleteBookDetailByBookId(){
        assertThat(bookRepositoryImpl.
                createBookRecord(new Book("Java Fundamental", "John Doe")))
                .isTrue();
        assertThat(bookRepositoryImpl.
                createBookRecord(new Book("Java APIs", "John Smith")))
                .isTrue();
        assertThat(bookRepositoryImpl.
                createBookRecord(new Book("Java Spring", "David")))
                .isTrue();

        assertThat(bookRepositoryImpl.getAllBooks().size()).isEqualTo(3);

        assertThat(bookRepositoryImpl.deleteBooksByBookId(1)).isTrue();
        assertThat(bookRepositoryImpl.getAllBooks().size()).isEqualTo(2);
    }
}
