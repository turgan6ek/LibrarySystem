package kz.iitu.demo.repository;

import kz.iitu.demo.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByIsAvailableTrue();
    @Query(value = "SELECT * FROM book where is_available is 1 AND (author LIKE ?1 OR " +
            "name LIKE ?2 OR description LIKE ?3)", nativeQuery = true)
    List<Book> findByParameters(String author, String name, String description);
}
