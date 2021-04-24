package kz.iitu.demo.controller;

import kz.iitu.demo.entity.Member;
import kz.iitu.demo.repository.MemberRepository;
import kz.iitu.demo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {
    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @GetMapping("")
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }
    @GetMapping("/{id}")
    public Member getMemberById(@PathVariable Long id) {
        return memberService.getMember(id);
    }
    @PostMapping("/register/")
    public Member registerMember(@RequestBody Member member){
        return memberRepository.saveAndFlush(member);
    }
}
