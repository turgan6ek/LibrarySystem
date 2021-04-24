package kz.iitu.demo.controller;

import kz.iitu.demo.entity.Book;
import kz.iitu.demo.entity.Issue;
import kz.iitu.demo.entity.Member;
import kz.iitu.demo.service.BookService;
import kz.iitu.demo.service.IssueService;
import kz.iitu.demo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@Controller
public class LibrarySystem {
    @Autowired
    private BookService bookService;
    @Autowired
    private IssueService issueService;
    @Autowired
    private MemberService memberService;
    Scanner scan = new Scanner(System.in);
    Member member = null;
    @PostConstruct
    void menu() {
        int choice;
        do {
            System.out.println("1.Login(for members)");
            System.out.println("2.Library Management");
            System.out.println("3.Library Management");
            System.out.println("4.Exit");
            System.out.print("\nEnter the number:");
            choice = scan.nextInt();
            switch (choice) {
                case 1: {
                    memberMenu();
                    break;
                }
                case 2: {
                    registerMember();
                    break;
                }
                case 3: {
                    adminMenu();
                    break;
                }
                case 4: {
                    System.out.println("Good bye!!!");
                    break;
                }
                default: {
                    System.out.println("Wrong Input");
                }
            }
        }
        while (choice != 4);

    }

    private void registerMember() {
        System.out.println("Enter your name please: ");
        String name = scan.nextLine();
        Member member = new Member();
        member.setName(name);
        memberService.addMember(member);
        System.out.println("Congratulations, you have successfully registered!!!" + "\nYour id is: " + memberService.generateID());
    }

    void memberMenu() {
        int choice;
        System.out.print("\nEnter the id please: ");
        Long id = scan.nextLong();
        Member member = memberService.getMember(id);
        if (member.getId() != null) {
            do {
                System.out.println("1.Issue book");
                System.out.println("2.Return book");
                System.out.println("3.Exit");
                System.out.print("\nEnter the number: ");
                choice = scan.nextInt();
                switch (choice) {
                    case 1: {
                        System.out.println("What book do you want to issue? ");
                        String info = scan.next();
                        List<Book> allBooks = bookService.findAll();
                        List<Book> availableBookList = bookService.findByInfo(info, info, info);
                        if (availableBookList.size() > 0) {
                            System.out.println("Here is the list of available books: ");
                            for (Book b: availableBookList) {
                                b.toString();
                                System.out.println("\n");
                            }
                            System.out.print("Enter the id of the book that you want to issue: ");
                            Long bookId = scan.nextLong();
                            Issue issue = new Issue();
                            issue.setStatus("REQUESTED");
                            issue.setDateOfIssue(new java.sql.Date(new java.util.Date().getTime()));
                            issue.setBook(bookService.getOne(bookId));
                            issueService.makeRequest(issue);
                            System.out.println("Thanks for requesting... You can watch the request status on another page.");
                            System.out.println("Please ask manager to accept your request.");
                        }
                        else {
                            System.out.println("There are no such books are available.");
                        }
                        break;
                    }
                    case 2: {
                        System.out.println("Here are the books that you are issued: ");
                        for (Issue issue: member.getIssues()) {
                            if (issue.getStatus().equals("ISSUED")) {
                                Book book = issue.getBook();
                                System.out.println("ISSUE ID : " + issue.getId());
                                book.toString();
                                System.out.println();
                            }
                        }
                        System.out.println("Enter the id of issue please: ");
                        Long issueID = scan.nextLong();
                        if (issueService.findByID(id) != null) {
                            issueService.returnBook(issueService.findByID(id));
                            System.out.println("Thanks for returning the book!!!");
                        }
                        else {
                            System.out.println("Wrong ID (No such issue found!!!)");
                        }

                        break;
                    }
                    case 3:{
                        System.out.println("Goodbye, " + member.getName());
                        break;
                    }
                    default: {
                        System.out.println("Wrong Input");
                    }
                }
            }
            while (choice != 3);
        }
        else {
            System.out.println("ID not found in the database, please register!!! ");
        }
    }
    void adminMenu() {
        int choice;
        do {
            System.out.println("1.Book list");
            System.out.println("2.Get Over due books");
            System.out.println("3.Accept all requests");
            System.out.println("4.Exit");
            System.out.print("\nEnter the number:");
            choice = scan.nextInt();
            switch (choice) {
                case 1: {
                    for(Book book: bookService.findAll()) {
                        book.toString();
                    }
                    break;
                }
                case 2: {
                    for (Issue issue: issueService.findOverdue()) {
                        System.out.println("IssueID: " + issue.getId() +
                                "Book name: " + issue.getBook().getName() +
                                "Status   : " + issue.getStatus() +
                                "Issued at: " + issue.getDateOfIssue() +
                                "Due Date : " + issue.getDueDate() +
                                "Taken by : " + issue.getMember().getName());
                    }
                    break;
                }
                case 3: {
                    issueService.acceptRequests();
                    System.out.println("Acceptable requests are accepted!!!");
                    break;
                }
                default: {
                    System.out.println("Wrong Input");
                }
            }
        }
        while (choice != 4);
    }
}
