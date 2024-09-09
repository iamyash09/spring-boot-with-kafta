package com.springkafka.demo.main.repository;

import com.springkafka.demo.main.dto.Book;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Implementation of the {@link BookRepository} interface
 */
@Repository
public class BookRepositoryImpl implements BookRepository{

    private Map<Integer, Book> bookList = new ConcurrentHashMap<>();
    private AtomicInteger at = new AtomicInteger(0);

    @Override
    public boolean createBookRecord(Book bookDTO) {
        bookDTO.setId(at.incrementAndGet());
        bookList.put(bookDTO.getId(), bookDTO);
        return true;
    }

    @Override
    public Map<Integer, Book> getAllBooks() {
        return bookList;
    }

    @Override
    public Book getBooksByBookId(int id) {
        if( bookList.containsKey(id) ){
            return bookList.get(id);
        }
        return null;
    }

    @Override
    public boolean updateBooksByBookId(int id, Book bookDTO) {
        if( bookList.containsKey(id) ) {
            bookDTO.setId(id);
            bookList.replace(id, bookDTO);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteBooksByBookId(int id) {
        if( bookList.containsKey(id) ){
            bookList.remove(id);
            return true;
        }
        return false;
    }
}
