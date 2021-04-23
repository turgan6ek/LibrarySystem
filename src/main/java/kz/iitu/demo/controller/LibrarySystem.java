package kz.iitu.demo.controller;

import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.util.Scanner;

@Controller
public class LibrarySystem {
    @PostConstruct
    void menu() {
        int choice;
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println("1.Login(for members)");
            System.out.println("2.Library Management");
        }
    }
    void memberMenu() {

    }
    void adminMenu() {

    }
}
