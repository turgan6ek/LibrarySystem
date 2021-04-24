package kz.iitu.demo.service.impl;

import kz.iitu.demo.entity.Book;
import kz.iitu.demo.repository.BookRepository;
import kz.iitu.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public void addBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void issueBook(Book book) {
        book.setAvailable(false);
        bookRepository.save(book);
    }

    @Override
    public void returnBook(Book book) {
        book.setAvailable(true);
        bookRepository.save(book);
    }

    @Override
    public void deleteBook(Book book) {
        bookRepository.delete(book);
    }

    @Override
    public List<Book> findByInfo(String author, String name, String description) {
        return bookRepository.findByAuthorContainingOrNameContainingOrDescriptionContainingAndIsAvailableTrue(author, name, description);
    }

    @Override
    public Book getOne(Long id) {
        return bookRepository.findById(id).get();
    }
}
