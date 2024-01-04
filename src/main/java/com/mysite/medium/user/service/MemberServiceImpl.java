package com.mysite.medium.user.service;

import com.mysite.medium.global.exception.AuthException;
import com.mysite.medium.global.exception.ErrorCode;
import com.mysite.medium.user.dto.CheckLoginDto;
import com.mysite.medium.user.dto.MemberCreateDto;
import com.mysite.medium.user.dto.MemberDtoMapper;
import com.mysite.medium.user.entity.Member;
import com.mysite.medium.user.repository.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberDtoMapper memberDtoMapper;

    @Transactional
    public void createUser(final MemberCreateDto memberCreateDto) {

        Optional<Member> duplicatedLoginId = memberRepository.findByUsername(memberCreateDto.getUsername());
        if (duplicatedLoginId.isPresent()) {
            throw new AuthException(ErrorCode.DUPLICATED_LOGIN_ID);
        }
        Optional<Member> duplicatedEmail = memberRepository.findByEmail(memberCreateDto.getEmail());
        if (duplicatedEmail.isPresent()) {
            throw new AuthException(ErrorCode.DUPLICATED_EMAIL);
        }

        memberCreateDto.checkEqualsPassword();

        final Member user = Member.builder()
                .username(memberCreateDto.getUsername())
                .email(memberCreateDto.getEmail())
                .password(passwordEncoder.encode(memberCreateDto.getPassword1()))
                .isPaid(memberCreateDto.getIsPaid())
                .build();

        memberRepository.save(user);
    }

    public CheckLoginDto checkLogin(final String username) {
        final Member member = this.memberRepository.findByUsername(username)
                .orElseThrow(() -> new AuthException(ErrorCode.IS_NOT_LOGIN));

        final CheckLoginDto memberDto = memberDtoMapper.memberToCheckLoginDto(member);

        return memberDto;
    }



}