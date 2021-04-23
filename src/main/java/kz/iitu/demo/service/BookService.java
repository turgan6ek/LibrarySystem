package kz.iitu.demo.service;

import kz.iitu.demo.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> findAll();
    void addBook(Book book);
    void issueBook(Book book);
    void returnBook(Book book);
    void deleteBook(Book book);
}
