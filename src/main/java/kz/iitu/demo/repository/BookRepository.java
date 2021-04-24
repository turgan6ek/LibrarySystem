package kz.iitu.demo.repository;

import kz.iitu.demo.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByIsAvailableTrue();
    List<Book> findByAuthorContainingOrNameContainingOrDescriptionContainingAndIsAvailableTrue(String author, String name, String description);
}
