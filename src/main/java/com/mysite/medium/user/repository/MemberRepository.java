package com.mysite.medium.user.repository;

import com.mysite.medium.user.entity.Member;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByUsername(String username);
	Optional<Member> findByEmail(String email);
}