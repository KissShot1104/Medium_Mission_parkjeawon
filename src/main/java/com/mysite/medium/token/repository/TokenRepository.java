package com.mysite.medium.token.repository;


import com.mysite.medium.token.entity.Token;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends CrudRepository<Token, Long> {

    Optional<Token> findByMemberId(Long memberId);
    void deleteByMemberId(Long memberId);

}
