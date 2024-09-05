package com.devkduck.duckshop.service;

import com.devkduck.duckshop.entity.Member;
import com.devkduck.duckshop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member saveMember(Member member) {
        validateUplicateMember(member);
        return memberRepository.save(member);
    }
    private void validateUplicateMember(Member member) {
        Member findMember = memberRepository.findByEmail(member.getEmail());
        if (findMember!=null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }
}
