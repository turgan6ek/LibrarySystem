package kz.iitu.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kz.iitu.demo.entity.Issue;
import kz.iitu.demo.entity.Member;
import kz.iitu.demo.repository.MemberRepository;
import kz.iitu.demo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
@Api(value = "Issue controller Class")
@ApiResponses( value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401, message = "You are not authorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Resource not found")
}
)
public class MemberController {
    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @GetMapping("")
    @ApiOperation(value = "To get list of members", response = List.class)
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "To get member by id", response = Member.class)
    public Member getMemberById(@PathVariable Long id) {
        return memberService.getMember(id);
    }

    @GetMapping("/register")
    @ApiOperation(value = "Called on registration", response = Member.class)
    public Member createMember(String name, String password) {
        Member member = new Member();
        member.setName(name);
        member.setPassword(password);
        memberService.addMember(member);
        return member;
    }

    @PutMapping("")
    @ApiOperation(value = "To change member details", response = Member.class)
    public Member registerMember(@RequestBody Member member){
        return memberRepository.saveAndFlush(member);
    }

    @GetMapping("/issues")
    @ApiOperation(value = "To get list of issues of specific member", response = List.class)
    public List<Issue> getMyIssues(@RequestParam Long id) {
        List<Issue> issues = memberService.getMember(id).getIssues();
        return issues;
    }
}
