package com.devkduck.duckshop.repository;

import com.devkduck.duckshop.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmail(String email); //중복된 이메일 검사하는 쿼리메서드
}
