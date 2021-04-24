package kz.iitu.demo.controller;

import kz.iitu.demo.entity.Book;
import kz.iitu.demo.entity.Member;
import kz.iitu.demo.repository.BookRepository;
import kz.iitu.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.PrintWriter;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    BookService bookService;
    @Autowired
    BookRepository bookRepository;
    @GetMapping("")
    public List<Book> getAllBooks() {
        return bookService.findAll();
    }
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookRepository.findById(id).get();
    }

    @GetMapping("/search/")
    public List<Book> getByInfo(@RequestParam String info) {
        return bookRepository.findByAuthorContainingOrNameContainingOrDescriptionContainingAndIsAvailableTrue(info, info, info);
    }
    @PostMapping("")
    public Book addBook(@RequestBody Book book){
        return bookRepository.saveAndFlush(book);
    }

    @DeleteMapping("/{id}")
    public Book deleteById(@PathVariable Long id) {
        Book book = bookRepository.findById(id).get();
        bookRepository.delete(book);
        return book;
    }
    @PutMapping("/return/{id}")
    public Book returnBook(@PathVariable Long id) {
        Book book = bookRepository.findById(id).get();
        book.setAvailable(true);
        return bookRepository.saveAndFlush(book);
    }
}
