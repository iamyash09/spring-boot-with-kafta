package com.springkafka.demo.main.repository;

import com.springkafka.demo.main.dto.Book;

import java.util.Map;

/**
 * An interface for all books transactions
 *
 */
public interface BookRepository {

    /**
     * Create new books
     *
     * @param bookDTO title, author
     * @return return create book status
     */
    public boolean createBookRecord(Book bookDTO);

    /**
     * Get all book details
     *
     * @return return list of all books
     */
    public Map<Integer, Book> getAllBooks();

    /**
     * Get book details based on book id
     *
     * @param id
     * @return book details
     */
    public Book getBooksByBookId(int id);

    /**
     * Update book details based on book id
     *
     * @param id
     * @param bookDTO title, author
     *
     * @return return create book status
     */
    public boolean updateBooksByBookId(int id, Book bookDTO);

    /**
     * Delete book detail by book id
     *
     * @param id
     * @return return delete book status
     */
    public boolean deleteBooksByBookId(int id);
}
