package kz.iitu.demo.service.impl;

import kz.iitu.demo.entity.Issue;
import kz.iitu.demo.repository.IssueRepository;
import kz.iitu.demo.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class IssueServiceImpl implements IssueService {
    @Autowired
    private IssueRepository issueRepository;

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
        issues.removeIf(i -> i.getDueDate().before(date));
        return issues;
    }

    @Override
    public List<Issue> findByID(Long id) {
        return issueRepository.findAllByMember_Id(id);
    }

    @Override
    public void returnBook(Issue issue) {
        issueRepository.delete(issue);
    }

    @Override
    public void acceptRequests() {

    }
}
