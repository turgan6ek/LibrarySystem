package kz.iitu.demo.service;

import kz.iitu.demo.entity.Member;

public interface MemberService {
    Member getMember(Long id);
    void addMember(Member member);
    int generateID();
}
