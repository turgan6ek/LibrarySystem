package kz.iitu.demo.controller;

import kz.iitu.demo.entity.Book;
import kz.iitu.demo.entity.Issue;
import kz.iitu.demo.entity.Member;
import kz.iitu.demo.repository.IssueRepository;
import kz.iitu.demo.service.BookService;
import kz.iitu.demo.service.IssueService;
import kz.iitu.demo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/issues")
public class IssueController {
    @Autowired
    IssueService issueService;
    @Autowired
    MemberService memberService;
    @Autowired
    BookService bookService;
    @Autowired
    IssueRepository issueRepository;

    @GetMapping("")
    public List<Issue> findAll() {
        return issueRepository.findAll();
    }
    @GetMapping("/over")
    public List<Issue> findOverDue() {
        return issueService.findOverdue();
    }
    @PostMapping("/make")
    public Issue makeRequest(@RequestParam Long bookID, @RequestParam Long userID) {
        Book book = bookService.getOne(bookID);
        Member member = memberService.getMember(userID);
        Issue issue = new Issue();
        issue.setBook(book);
        issue.setMember(member);
        issue.setStatus("REQUESTED");
        return issueRepository.saveAndFlush(issue);
    }
    @GetMapping("/{id}")
    public Issue getOne(@PathVariable Long id) {
        return issueRepository.getOne(id);
    }
    @PutMapping("/{id}")
    public Issue returnBook(@PathVariable Long id, @RequestBody Issue issue) {
        java.sql.Date date = new java.sql.Date(new java.util.Date().getTime());
        issue.setReturnDate(date);
        issue.setStatus("RETURNED");
        issueRepository.saveAndFlush(issue);
        return issue;
    }
}
