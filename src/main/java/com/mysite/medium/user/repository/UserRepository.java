package com.mysite.medium.user.repository;

import com.mysite.medium.user.entity.SiteUser;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
	Optional<SiteUser> findByUsername(String username);
	Optional<SiteUser> findByEmail(String email);
}