package kz.iitu.demo.repository;

import kz.iitu.demo.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long> {
    List<Issue> findAllByDueDateAfter(Date date);
    List<Issue> findAllByMember_Id(Long id);
}
