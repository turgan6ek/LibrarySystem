package kz.iitu.demo.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String author;
    private String description;
    private boolean isAvailable;
    @OneToOne(mappedBy = "book", fetch = FetchType.LAZY)
    private Issue issue;
    public String getName() {
        return name;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id:" + id + '\'' +
                ", name:'" + name + '\'' +
                ", author:'" + author + '\'' +
                ", description:'" + description + '\'' +
                ", isAvailable:" + isAvailable +
                '}';
    }
}
