package kz.iitu.demo.service.impl;

import kz.iitu.demo.entity.Member;
import kz.iitu.demo.repository.MemberRepository;
import kz.iitu.demo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService, UserDetailsService {
    @Autowired
    MemberRepository memberRepository;
    @Override
    public Member getMember(Long id) {
        return memberRepository.findById(id).get();
    }
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void addMember(Member member) {
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        memberRepository.save(member);
    }

    @Override
    public int generateID() {
        return memberRepository.findAll().size();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Member member = memberRepository.findById(Long.valueOf(s)).get();
        if (member == null) {
            throw new UsernameNotFoundException("User not found!!!");
        }
        return member;
    }
}
