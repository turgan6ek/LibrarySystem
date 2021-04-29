package kz.iitu.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(value = "Issue controller Class")
@ApiResponses( value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "You are not authorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Resource not found")
}
)
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
    @ApiOperation(value = "Method to get list of all issues(requests for a book)", response = List.class)
    public List<Issue> findAll() {
        return issueRepository.findAll();
    }

    @GetMapping("/over")
    @ApiOperation(value = "Method to get list of all overdue issues and pending requests", response = List.class)
    public List<Issue> findOverDue() {
        return issueService.findOverdue();
    }

    @PostMapping("/make")
    @ApiOperation(value = "Method to make a request", response = Issue.class)
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
    @ApiOperation(value = "To get issue by id", response = Issue.class)
    public Issue getOne(@PathVariable Long id) {
        return issueRepository.getOne(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Called when someone returns the book", response = Issue.class)
    public Issue returnBook(@PathVariable Long id, @RequestBody Issue issue) {
        issueService.returnBook(issue);
        return issue;
    }
}
