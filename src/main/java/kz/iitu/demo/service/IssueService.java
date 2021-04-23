package kz.iitu.demo.service;

import kz.iitu.demo.entity.Issue;

import java.util.List;

public interface IssueService {
    List<Issue> findAll();
    void makeRequest(Issue issue);
    List<Issue> findOverdue();
    List<Issue> findByID(Long id);
    void returnBook(Issue issue);
    void acceptRequests();
}
