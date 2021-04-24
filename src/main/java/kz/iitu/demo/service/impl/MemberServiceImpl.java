package kz.iitu.demo.service.impl;

import kz.iitu.demo.entity.Member;
import kz.iitu.demo.repository.MemberRepository;
import kz.iitu.demo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    MemberRepository memberRepository;
    @Override
    public Member getMember(Long id) {
        return memberRepository.findById(id).get();
    }

    @Override
    public void addMember(Member member) {
        memberRepository.save(member);
    }

    @Override
    public int generateID() {
        return memberRepository.findAll().size();
    }

}
