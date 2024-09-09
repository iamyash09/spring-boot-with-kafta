package com.springkafka.demo.main.service;

import com.springkafka.demo.main.dto.Book;
import com.springkafka.demo.main.repository.BookRepositoryImpl;
import com.springkafka.demo.main.service.kafka.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BookService {

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private BookRepositoryImpl bookRepositoryImpl;

    public boolean createBookRecord(Book bookDTO) {
        if( bookRepositoryImpl.createBookRecord(bookDTO) ){
            kafkaProducer.sendMessage("New book record created");
            return true;
        }
        return false;
    }
    public Map<Integer, Book> getAllBooks() {
        return bookRepositoryImpl.getAllBooks();
    }

    public Book getBooksByBookId(int id){
        return bookRepositoryImpl.getBooksByBookId(id);
    }

    public boolean updateBooksByBookId(int id, Book bookDTO){
        if ( getBooksByBookId(id) != null) {
            return bookRepositoryImpl.updateBooksByBookId(id, bookDTO);
        }
        return false;
    }

    public boolean deleteBooksByBookId(int id){
        if ( getBooksByBookId(id) != null) {
            return bookRepositoryImpl.deleteBooksByBookId(id);
        }
        return false;
    }

}
