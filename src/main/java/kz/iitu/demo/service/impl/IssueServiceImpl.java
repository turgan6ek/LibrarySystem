package kz.iitu.demo.service.impl;

import kz.iitu.demo.entity.Book;
import kz.iitu.demo.entity.Issue;
import kz.iitu.demo.repository.BookRepository;
import kz.iitu.demo.repository.IssueRepository;
import kz.iitu.demo.service.BookService;
import kz.iitu.demo.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
@Service
public class IssueServiceImpl implements IssueService {
    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @Override
    public List<Issue> findAll() {
        return issueRepository.findAll();
    }

    @Override
    public void makeRequest(Issue issue) {
        issueRepository.save(issue);
    }

    @Override
    public List<Issue> findOverdue() {
        List<Issue> issues = issueRepository.findAll();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        java.sql.Date date = new java.sql.Date(new java.util.Date().getTime());
        for (Issue issue : issues) {
            if (issue.getDueDate() != null) {
                if (issue.getDueDate().before(date) ) {
                    issues.remove(issue);
                }
            }

        }
        return issues;
    }

    @Override
    public Issue findByID(Long id) {
        return issueRepository.getOne(id);
    }

    @Override
    public void returnBook(Issue issue) {
        java.sql.Date date = new java.sql.Date(new java.util.Date().getTime());
        issue.setReturnDate(date);
        issue.setStatus("RETURNED");
        issueRepository.save(issue);
        bookService.returnBook(issue.getBook());
    }

    @Override
    public void acceptRequests() {
        List<Issue> issues = issueRepository.findAllByStatus("REQUESTED");
        List<Book> books = bookRepository.findByIsAvailableTrue();
        for (Book book: books) {
            for (Issue issue: issues) {
                if ( book.getId() == issue.getBook().getId()) {
                    bookService.issueBook(book);
                    LocalDate localDate = LocalDate.now().plusDays(14);
                    issue.setDueDate(java.sql.Date.valueOf(localDate));
                    issue.setStatus("ISSUED");
                    issueRepository.save(issue);
                    System.out.println("Accepted ID: " + issue.getId());
                }
            }
        }
    }
}
