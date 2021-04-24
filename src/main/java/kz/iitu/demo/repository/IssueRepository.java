package kz.iitu.demo.repository;

import kz.iitu.demo.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    List<Issue> findAllByDueDateAfter(Date date);
    List<Issue> findAllByMember_Id(Long id);
    List<Issue> findAllByStatus(String status);
}
