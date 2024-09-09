package com.springkafka.demo.main.dto;

/**
 * A class representing properties of book
 */
public class Book {
    private int id;
    private String title;
    private String author;

    public Book(String title, String author){
        this.title = title;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString(){
        return "{"
                +  "Id:" + id + ","
                +  "Title:" + title + ","
                +  "Author:" + author
                +
                "}";
    }
}
