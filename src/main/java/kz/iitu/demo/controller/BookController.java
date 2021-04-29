package kz.iitu.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(value = "Book controller Class")
@ApiResponses( value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "You are not authorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Resource not found")
}
)
public class BookController {
    @Autowired
    BookService bookService;
    @Autowired
    BookRepository bookRepository;

    @GetMapping("")
    @ApiOperation(value = "Method to get list of books", response = List.class)
    public List<Book> getAllBooks() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "To get book by its id", response = Book.class)
    public Book getBookById(@PathVariable Long id) {
        return bookRepository.findById(id).get();
    }

    @GetMapping("/search/")
    @ApiOperation(value = "Method which searches the book by author, name and issue(if it is available)", response = List.class)
    public List<Book> getByInfo(@RequestParam String info) {
        return bookRepository.findByAuthorContainingOrNameContainingOrDescriptionContainingAndIsAvailableTrue(info, info, info);
    }

    @PostMapping("")
    @ApiOperation(value = "Method for adding a book.", response = Book.class)
    public Book addBook(@RequestBody Book book){
        return bookRepository.saveAndFlush(book);
    }


    @DeleteMapping("/{id}")
    @ApiOperation(value = "Method for deleting the book.")
    public Book deleteById(@PathVariable Long id) {
        Book book = bookRepository.findById(id).get();
        bookRepository.delete(book);
        return book;
    }

    @PutMapping("/return/{id}")
    @ApiOperation(value = "Method called when book is returned to the library", response = Book.class)
    public Book returnBook(@PathVariable Long id) {
        Book book = bookRepository.findById(id).get();
        book.setAvailable(true);
        return bookRepository.saveAndFlush(book);
    }
}
